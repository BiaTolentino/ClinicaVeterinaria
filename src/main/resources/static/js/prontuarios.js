import * as api from './api.js';

const form = document.getElementById('formProntuario');
const tbl = document.querySelector('#tblProntuarios tbody');
const search = document.getElementById('searchProntuario');
const selectPet = document.getElementById('petId');
const hiddenId = document.getElementById('idPetHidden'); // referência direta

// 🔹 Carrega lista de pets no <select>
async function loadPets() {
    let pets = await api.listEntity('pets');
    if (!Array.isArray(pets)) pets = [];

    selectPet.innerHTML = '<option value="">Selecione um pet...</option>';
    pets.forEach(p => {
        const opt = document.createElement('option');
        opt.value = p.idPet;
        opt.textContent = `${p.nome} (${p.especie ?? ''}) - Tutor: ${p.cliente?.nome ?? 'Sem tutor'}`;
        selectPet.appendChild(opt);
    });
}

// 🔹 Carrega todos os prontuários
async function loadProntuarios() {
    let prontuarios = await api.listEntity('prontuarios');
    if (!Array.isArray(prontuarios)) prontuarios = [];
    render(prontuarios);
}

// 🔹 Renderiza tabela
function render(list) {
    tbl.innerHTML = '';
    if (!list.length) {
        tbl.innerHTML = `<tr><td colspan="6" class="text-center text-muted">Nenhum prontuário encontrado.</td></tr>`;
        return;
    }

    list.forEach(p => {
        const id = p.idPet ?? '';
        const petNome = p.pet?.nome ?? '—';
        const tutor = p.pet?.cliente?.nome ?? '—';
        const obs = p.observacoesGerais ?? '';
        const ultima = p.ultimaAtualizacao ? p.ultimaAtualizacao.replace('T', ' ').substring(0, 16) : '—';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${id}</td>
            <td>${petNome}</td>
            <td>${tutor}</td>
            <td>${obs}</td>
            <td>${ultima}</td>
            <td class="text-center">
                <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${id}">
                    <i class="bi bi-pencil-square"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-del" data-id="${id}">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbl.appendChild(tr);
    });

    attachEvents(list);
}

// 🔹 Eventos dos botões editar/excluir
function attachEvents(list) {
    document.querySelectorAll('.btn-edit').forEach(btn => {
        btn.onclick = () => {
            const pront = list.find(p => p.idPet == btn.dataset.id);
            if (!pront) return;

            hiddenId.value = pront.idPet;
            document.getElementById('observacoes').value = pront.observacoesGerais ?? '';
            selectPet.value = pront.pet?.idPet ?? '';
        };
    });

    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.onclick = async () => {
            if (confirm('Deseja remover este prontuário?')) {
                await api.deleteEntity('prontuarios', btn.dataset.id);
                await loadProntuarios();
            }
        };
    });
}

// 🔹 Envio do formulário (criar/atualizar)
form.addEventListener('submit', async e => {
    e.preventDefault();

    const id = hiddenId.value;
    const prontuario = {
        petId: Number(selectPet.value),
        observacoesGerais: document.getElementById('observacoes').value
    };

    if (!prontuario.petId) {
        alert('Selecione um pet antes de salvar.');
        return;
    }

    if (id)
        await api.updateEntity('prontuarios', id, prontuario);
    else
        await api.createEntity('prontuarios', prontuario);

    form.reset();
    hiddenId.value = ''; // limpa ID
    await loadProntuarios();
});

// 🔍 Filtro de busca
search.addEventListener('input', async () => {
    const termo = search.value.trim().toLowerCase();
    let prontuarios = await api.listEntity('prontuarios');
    prontuarios = prontuarios.filter(p =>
        (p.pet?.nome ?? '').toLowerCase().includes(termo) ||
        (p.pet?.cliente?.nome ?? '').toLowerCase().includes(termo)
    );
    render(prontuarios);
});

// 🔹 Inicializa ao carregar
window.addEventListener('load', async () => {
    await loadPets();
    await loadProntuarios();
});
