import * as api from './api.js';

const tbl = document.querySelector('#tblConsultas tbody');
const form = document.getElementById('formConsulta');
const petSelect = document.getElementById('petId');
const vetSelect = document.getElementById('veterinarioId');

const filtroPet = document.getElementById('filtroPet');
const filtroVet = document.getElementById('filtroVet');
const filtroInicio = document.getElementById('filtroInicio');
const filtroFim = document.getElementById('filtroFim');
const btnFiltrar = document.getElementById('btnFiltrar');
const btnLimpar = document.getElementById('btnLimpar');

let consultasCache = [];

// 🐾 Carrega as opções de Pets e Veterinários
async function loadOptions() {
    const [pets, vets] = await Promise.all([
        api.listEntity('pets'),
        api.listEntity('veterinarios')
    ]);

    petSelect.innerHTML = '<option value="">Selecione o Pet</option>';
    pets.forEach(p => {
        const opt = document.createElement('option');
        opt.value = p.idPet;
        opt.textContent = `${p.nome} (${p.especie})`;
        petSelect.appendChild(opt);
    });

    vetSelect.innerHTML = '<option value="">Selecione o Veterinário</option>';
    vets.forEach(v => {
        const opt = document.createElement('option');
        opt.value = v.idVet;
        opt.textContent = `${v.nome} - ${v.especialidade || 'Clínico Geral'}`;
        vetSelect.appendChild(opt);
    });
}

// 🔹 Carrega filtros (reutiliza listas)
async function loadFilterOptions() {
    const [pets, vets] = await Promise.all([
        api.listEntity('pets'),
        api.listEntity('veterinarios')
    ]);

    filtroPet.innerHTML = '<option value="">Todos</option>';
    pets.forEach(p => {
        const opt = document.createElement('option');
        opt.value = p.idPet;
        opt.textContent = `${p.nome} (${p.especie})`;
        filtroPet.appendChild(opt);
    });

    filtroVet.innerHTML = '<option value="">Todos</option>';
    vets.forEach(v => {
        const opt = document.createElement('option');
        opt.value = v.idVet;
        opt.textContent = `${v.nome} - ${v.especialidade || ''}`;
        filtroVet.appendChild(opt);
    });
}

// 📋 Lista todas as consultas
async function loadConsultas() {
    let consultas = await api.listEntity('consultas');
    consultasCache = Array.isArray(consultas) ? consultas : [];
    render(consultasCache);
}

// 🔎 Aplica filtros compostos (Pet, Vet, Data)
async function aplicarFiltros() {
    const params = new URLSearchParams();

    if (filtroPet.value) params.append('idPet', filtroPet.value);
    if (filtroVet.value) params.append('idVet', filtroVet.value);
    if (filtroInicio.value)
        params.append('inicio', filtroInicio.value + 'T00:00:00');
    if (filtroFim.value)
        params.append('fim', filtroFim.value + 'T23:59:59');

    const url = 'consultas/filtro?' + params.toString();
    const filtradas = await api.listEntity(url);
    render(Array.isArray(filtradas) ? filtradas : []);
}

// 🔄 Limpar filtros
btnLimpar.addEventListener('click', async () => {
    filtroPet.value = '';
    filtroVet.value = '';
    filtroInicio.value = '';
    filtroFim.value = '';
    await loadConsultas();
});

btnFiltrar.addEventListener('click', e => {
    e.preventDefault();
    aplicarFiltros();
});

// 🖋️ Renderiza tabela
function render(list) {
    tbl.innerHTML = '';
    if (!list.length) {
        tbl.innerHTML = `<tr><td colspan="7" class="text-center text-muted">Nenhuma consulta encontrada.</td></tr>`;
        return;
    }

    list.forEach(c => {
        const dataFormatada = c.dataHora
            ? new Date(c.dataHora).toLocaleString('pt-BR')
            : '';

        const tr = document.createElement('tr');
        tr.innerHTML = `
      <td>${c.idConsulta ?? ''}</td>
      <td>${dataFormatada}</td>
      <td>${c.pet?.nome ?? ''}</td>
      <td>${c.veterinario?.nome ?? ''}</td>
      <td>${c.motivo ?? ''}</td>
      <td>${c.diagnostico ?? ''}</td>
      <td class="text-center">
        <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${c.idConsulta}">
          <i class="bi bi-pencil-square"></i>
        </button>
        <button class="btn btn-sm btn-danger btn-del" data-id="${c.idConsulta}">
          <i class="bi bi-trash"></i>
        </button>
      </td>`;
        tbl.appendChild(tr);
    });

    attachButtons(list);
}

// ✏️ Editar / Excluir
function attachButtons(list) {
    document.querySelectorAll('.btn-edit').forEach(b => {
        b.onclick = () => {
            const c = list.find(x => x.idConsulta == b.dataset.id);
            if (!c) return;
            document.getElementById('consultaId').value = c.idConsulta;
            document.getElementById('dataHora').value = c.dataHora
                ? c.dataHora.replace(' ', 'T')
                : '';
            petSelect.value = c.pet?.idPet || '';
            vetSelect.value = c.veterinario?.idVet || '';
            document.getElementById('motivo').value = c.motivo || '';
            document.getElementById('diagnostico').value = c.diagnostico || '';
        };
    });

    document.querySelectorAll('.btn-del').forEach(b => {
        b.onclick = async () => {
            if (confirm('Deseja excluir esta consulta?')) {
                await api.deleteEntity('consultas', b.dataset.id);
                await loadConsultas();
            }
        };
    });
}

// 💾 Salvar / Atualizar
form.addEventListener('submit', async e => {
    e.preventDefault();

    const id = document.getElementById('consultaId').value;
    const payload = {
        dataHora: document.getElementById('dataHora').value,
        motivo: document.getElementById('motivo').value,
        diagnostico: document.getElementById('diagnostico').value,
        petId: Number(petSelect.value),
        veterinarioId: Number(vetSelect.value)
    };

    if (id) await api.updateEntity('consultas', id, payload);
    else await api.createEntity('consultas', payload);

    form.reset();
    await loadConsultas();
});

// 🚀 Inicialização
window.addEventListener('load', async () => {
    await loadOptions();
    await loadFilterOptions();
    await loadConsultas();
});
