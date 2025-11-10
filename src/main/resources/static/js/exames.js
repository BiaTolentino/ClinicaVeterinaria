import * as api from './api.js';

const form = document.getElementById('formExame');
const tblBody = document.querySelector('#tblExames tbody');
const search = document.getElementById('searchExame');
const selectConsulta = document.getElementById('consultaId');

let examesCache = [];

// Carrega consultas e preenche select (com placeholder)
async function loadConsultas() {
    try {
        const consultas = await api.listEntity('consultas') || [];

        // placeholder claro e garantido como primeira opção
        selectConsulta.innerHTML = '<option value="">Selecione a consulta...</option>';

        consultas.forEach(c => {
            const id = c.idConsulta ?? c.id ?? '';
            // formata data e nomes defensivamente
            const data = (c.dataHora ?? c.data ?? '').toString().substring(0, 10);
            const petNome = c.pet?.nome ?? 'Pet';
            const clienteNome = c.pet?.cliente?.nome ?? '';
            const label = `${data} - ${petNome}${clienteNome ? ' (' + clienteNome + ')' : ''}`;

            const opt = document.createElement('option');
            opt.value = id;
            opt.textContent = `Consulta #${id} - ${label}`;
            selectConsulta.appendChild(opt);
        });

        // garante que nada esteja selecionado por padrão
        selectConsulta.value = '';
        selectConsulta.selectedIndex = 0;
    } catch (err) {
        console.error('Erro ao carregar consultas:', err);
    }
}

// Carrega exames e renderiza
async function loadExames() {
    try {
        const exames = await api.listEntity('exames');
        examesCache = Array.isArray(exames) ? exames : [];
        render(examesCache);
    } catch (err) {
        console.error('Erro ao carregar exames:', err);
        examesCache = [];
        render([]);
    }
}

// Renderiza tabela com compatibilidade de campos
function render(list) {
    tblBody.innerHTML = '';
    if (!list || !list.length) {
        tblBody.innerHTML = `<tr><td colspan="6" class="text-center text-muted">Nenhum exame encontrado.</td></tr>`;
        return;
    }

    list.forEach(e => {
        const id = e.idExame ?? e.id ?? e.id_exame ?? '';
        const tipo = e.tipo ?? '';
        const resultado = e.resultado ?? '';
        const data = (e.dataExame ?? e.data_exame ?? e.dataHora ?? '').toString().substring(0, 10);
        const consultaId = e.consulta?.idConsulta ?? e.idConsulta ?? e.id_consulta ?? '';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${id}</td>
            <td>${escapeHtml(tipo)}</td>
            <td>${escapeHtml(resultado)}</td>
            <td>${data}</td>
            <td>${consultaId}</td>
            <td class="text-center">
                <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${id}">
                    <i class="bi bi-pencil-square"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-del" data-id="${id}">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tblBody.appendChild(tr);
    });

    attachEvents();
}

// associa eventos editar/excluir
function attachEvents() {
    document.querySelectorAll('.btn-edit').forEach(btn => {
        btn.onclick = () => {
            const id = btn.dataset.id;
            const exame = examesCache.find(x => (x.idExame ?? x.id ?? x.id_exame) == id);
            if (!exame) return;

            // preenche campos para edição
            document.getElementById('exameId').value = exame.idExame ?? exame.id ?? exame.id_exame ?? '';
            document.getElementById('tipo').value = exame.tipo ?? '';
            document.getElementById('resultado').value = exame.resultado ?? '';
            document.getElementById('dataExame').value = (exame.dataExame ?? exame.data_exame ?? '').toString().substring(0, 10);

            // Se a consulta ainda não existir no select (ex: foi carregada depois), forçamos a carregar e então setar
            if (!Array.from(selectConsulta.options).some(o => o.value == (exame.consulta?.idConsulta ?? exame.idConsulta ?? exame.id_consulta ?? ''))) {
                loadConsultas().then(() => {
                    selectConsulta.value = exame.consulta?.idConsulta ?? exame.idConsulta ?? exame.id_consulta ?? '';
                });
            } else {
                selectConsulta.value = exame.consulta?.idConsulta ?? exame.idConsulta ?? exame.id_consulta ?? '';
            }
        };
    });

    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.onclick = async () => {
            if (!confirm('Deseja remover este exame?')) return;
            try {
                await api.deleteEntity('exames', btn.dataset.id);
                await loadExames();
            } catch (err) {
                alert('Erro ao excluir: ' + (err.message || err));
            }
        };
    });
}

// submeter formulário (create ou update)
form.addEventListener('submit', async ev => {
    ev.preventDefault();
    const id = document.getElementById('exameId').value;
    const payload = {
        tipo: document.getElementById('tipo').value,
        resultado: document.getElementById('resultado').value,
        dataExame: document.getElementById('dataExame').value,
        consultaId: Number(selectConsulta.value) || null
    };

    try {
        if (id) {
            await api.updateEntity('exames', id, payload);
        } else {
            // garantir que não enviamos id no POST
            await api.createEntity('exames', payload);
        }
        form.reset();
        document.getElementById('exameId').value = '';
        // garantir select limpo após salvar um novo
        selectConsulta.value = '';
        await loadExames();
    } catch (err) {
        console.error('Erro ao salvar exame:', err);
        alert('Erro ao salvar exame: ' + (err.message || err));
    }
});

// filtro de busca simples
search?.addEventListener('input', () => {
    const termo = search.value.trim().toLowerCase();
    if (!termo) return render(examesCache);
    const filtrados = examesCache.filter(e =>
        (e.tipo ?? '').toString().toLowerCase().includes(termo) ||
        (e.resultado ?? '').toString().toLowerCase().includes(termo) ||
        (String(e.consulta?.idConsulta ?? e.idConsulta ?? e.id_consulta ?? '')).includes(termo) ||
        (e.consulta?.pet?.nome ?? '').toString().toLowerCase().includes(termo)
    );
    render(filtrados);
});

// utilitário para evitar injeção simples na tabela
function escapeHtml(str = '') {
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
}

// inicialização: carrega consultas primeiro (garante que select exista) e depois exames
window.addEventListener('load', async () => {
    await loadConsultas();
    await loadExames();
});
