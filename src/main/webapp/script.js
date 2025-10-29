const funcoes = document.querySelectorAll('.FUNCS')
let indice = 0
const containerCarrossel = document.querySelector('.FUNCS-CARROSSEL')
let interval;
const tempo = 50000;

// CARROSSEL - SETAS

document.getElementById('FUNCS-PROXIMO').addEventListener('click', () => {
    funcoes[indice].classList.remove('ativo')
    indice = (indice + 1) % funcoes.length
    funcoes[indice].classList.add('ativo')
} )

document.getElementById('FUNCS-ANTERIOR').addEventListener('click', () => {
    funcoes[indice].classList.remove('ativo')
    indice = (indice - 1 + funcoes.length) % funcoes.length
    funcoes[indice].classList.add('ativo')
} )

// CARROSSEL - ROLAGEM AUTOMÁTICA

function trocar(novoIndice){
    funcoes[indice]?.classList.remove('ativo')
    indice = novoIndice
    funcoes[indice]?.classList.add('ativo')
}

function rolagemAuto(){
    clearInterval(interval)
    interval = setInterval(() => {
        const proximo = (indice + 1) % funcoes.length
        trocar(proximo)
    }, tempo)
}

function pararRolagem(){
    clearInterval(interval)
}

const areaDeInteracao = document.getElementById('FUNC');

areaDeInteracao.addEventListener('mouseleave', pararRolagem)
areaDeInteracao.addEventListener('mouseleave', rolagemAuto)

document.addEventListener('DOMContentLoaded', () => {
    if (funcoes.length > 0){
        funcoes[indice].classList.add('ativo')
        rolagemAuto()
    }
})

// MUNDANÇA DE PLANOS

const planosDados = {
    "basico": {
        "anual": {
            preco: "R$14.800,00",
            tipo: "Anual"
        },
        "semestral": {
            preco: "R$7.900,00",
            tipo: "Semestral"
        },
        "trimestral":{
            preco:"R$4.450,00",
            tipo: "Trimestral"
        }
    },
    "premium": {
        "anual": {
            preco: "R$38.000,00",
            tipo: "Anual"
        },
        "semestral": {
            preco: "R$19.500,00",
            tipo: "Semestral"
        },
        "trimestral":{
            preco:"R$10.250,00",
            tipo: "Trimestral"
        }
    },
    "pro": {
        "anual": {
            preco: "R$25.500,00",
            tipo: "Anual"
        },
        "semestral": {
            preco: "R$13.250,00",
            tipo: "Semestral"
        },
        "trimestral":{
            preco:"R$7.125,00",
            tipo: "Trimestral"
        }
    }
}
const mapa = [
    {id: 'SP-1', plano: 'basico', container: document.querySelector('.SP-12e3')},
    {id: 'SP-2', plano: 'premium', container: document.querySelector('#SP-2')},
    {id: 'SP-3', plano: 'pro', container: document.querySelector('.SP-12e3:nth-child(3)')},
]

const botoes = {
    "anual": document.getElementById('botao-anual'),
    "semestral": document.getElementById('botao-semestral'),
    "trimestral": document.getElementById('botao-trimestral')
}

let planoAtual = 'anual'

function atualizar(novoPlano){
    Object.values(botoes).forEach(btn => btn.classList.remove('ativo-plano'))
    botoes[novoPlano].classList.add('ativo-plano')

    mapa.forEach(item => {
        const dadosPlano = planosDados[item.plano][novoPlano]
        const container = item.container
    
        const elementoPreco = container.querySelector('.SP-PRECO')
            if (elementoPreco){
                elementoPreco.textContent = dadosPlano.preco
            }
    
        const elementoTipo = container.querySelector('.SP-TIPO')
        if (elementoTipo){
            elementoTipo.textContent = dadosPlano.tipo
        }
        
    
    })

    planoAtual = novoPlano
}

document.addEventListener('DOMContentLoaded', () => {
    botoes["anual"].addEventListener('click', () => atualizar('anual'))
    botoes["semestral"].addEventListener('click', () => atualizar('semestral'))
    botoes["trimestral"].addEventListener('click', () => atualizar('trimestral'))

    atualizar(planoAtual)
})



