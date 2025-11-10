import * as api from './api.js';

const tbl = document.querySelector('#tblTratamentos tbody');
const form = document.getElementById('formTrat');
const search = document.getElementById('searchTrat');
const consultaSelect = document.getElementById('consultaId');
const medicamentoSelect = document.getElementById('medicamentoId');
const result = document.getElementById('result');

let tratamentosCache = [];
let optionsLoaded = false;

// Carrega opções (consultas + medicamentos)
async function loadOptions() {
    const [consultas, medicamentos] = await Promise.all([
        api.listEntity('consultas'),
        api.listEntity('medicamentos')
    ]);

    consultaSelect.innerHTML = '<option value="">Selecione</option>';
    (consultas || []).forEach(c => {
        const opt = document.createElement('option');
        opt.value = c.idConsulta;
        opt.textContent = `${c.idConsulta} - ${c.pet?.nome ?? 'Pet'} (${c.motivo ?? ''})`;
        consultaSelect.appendChild(opt);
    });

    medicamentoSelect.innerHTML = '<option value="">Selecione</option>';
    (medicamentos || []).forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.idMedicamento;
        opt.textContent = `${m.nome} (${m.fabricante ?? ''})`;
        medicamentoSelect.appendChild(opt);
    });

    optionsLoaded = true;
}

// Lista tratamentos
async function loadTratamentos() {
    const lista = await api.listEntity('tratamentos');
    tratamentosCache = Array.isArray(lista) ? lista : [];
    render(tratamentosCache);
}

// Filtro
search.addEventListener('input', () => {
    const termo = search.value.trim().toLowerCase();
    const filtrados = tratamentosCache.filter(t =>
        (t.pet || '').toString().toLowerCase().includes(termo) ||
        (t.medicamento || '').toString().toLowerCase().includes(termo) ||
        (t.motivo || '').toString().toLowerCase().includes(termo)
    );
    render(filtrados);
});

// Render tabela
function render(list) {
    tbl.innerHTML = '';
    if (!list.length) {
        tbl.innerHTML = `<tr><td colspan="7" class="text-center text-muted">Nenhum tratamento encontrado.</td></tr>`;
        return;
    }

    list.forEach(t => {
        const consultaTexto = t.idConsulta
            ? `${t.idConsulta} - ${t.pet ?? 'Pet'} (${t.motivo ?? ''})`
            : '';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${t.idConsulta ?? ''}</td>
            <td>${consultaTexto}</td>
            <td>${t.pet ?? ''}</td>
            <td>${t.medicamento ?? ''}</td>
            <td>${t.duracaoDias ?? ''}</td>
            <td>${t.observacoes ?? ''}</td>
            <td class="text-center">
                <button class="btn btn-sm btn-primary me-1 btn-edit" data-consulta="${t.idConsulta}" data-medicamento="${t.medicamentoId}">
                    <i class="bi bi-pencil-square"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-del" data-consulta="${t.idConsulta}" data-medicamento="${t.medicamentoId}">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbl.appendChild(tr);
    });

    attachButtons(list);
}

// Edit / Delete attach
function attachButtons(list) {
    document.querySelectorAll('.btn-edit').forEach(b => {
        b.onclick = () => {
            const consultaId = Number(b.dataset.consulta);
            const medicamentoId = Number(b.dataset.medicamento);
            const t = list.find(x => Number(x.idConsulta) === consultaId && Number(x.medicamentoId) === medicamentoId);
            if (!t) return;

            // Se options não carregaram ainda, aguardar
            if (!optionsLoaded) {
                setTimeout(() => b.click(), 100); // tenta novamente rápido
                return;
            }

            consultaSelect.value = t.idConsulta ?? '';
            medicamentoSelect.value = t.medicamentoId ?? '';
            document.getElementById('duracaoDias').value = t.duracaoDias ?? '';
            document.getElementById('observacoes').value = t.observacoes ?? '';

            form.dataset.editing = JSON.stringify({consultaId, medicamentoId});
        };
    });

    document.querySelectorAll('.btn-del').forEach(b => {
        b.onclick = async () => {
            if (!confirm('Deseja excluir este tratamento?')) return;

            const consultaId = Number(b.dataset.consulta);
            const medicamentoId = Number(b.dataset.medicamento);

            try {
                // usa rota /api/tratamentos/{consultaId}/{medicamentoId}
                await api.deleteEntity(`tratamentos/${consultaId}/${medicamentoId}`);
                await loadTratamentos();
            } catch (err) {
                alert('Erro ao excluir: ' + (err.message || err));
            }
        };
    });
}

// Salvar / Atualizar
form.addEventListener('submit', async e => {
    e.preventDefault();

    const payload = {
        consultaId: Number(consultaSelect.value),
        medicamentoId: Number(medicamentoSelect.value),
        duracaoDias: document.getElementById('duracaoDias').value ? Number(document.getElementById('duracaoDias').value) : null,
        observacoes: document.getElementById('observacoes').value || ''
    };

    try {
        if (form.dataset.editing) {
            const {consultaId, medicamentoId} = JSON.parse(form.dataset.editing);
            await api.updateEntity(`tratamentos/${consultaId}/${medicamentoId}`, payload);
            delete form.dataset.editing;
        } else {
            await api.createEntity('tratamentos', payload);
        }

        form.reset();
        await loadTratamentos();
    } catch (err) {
        result.innerHTML = `<div class="alert alert-danger">Erro: ${err.message || err}</div>`;
    }
});

// Inicialização
window.addEventListener('load', async () => {
    await loadOptions();
    await loadTratamentos();
});
