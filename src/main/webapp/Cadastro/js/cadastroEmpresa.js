cadastroEmpresa.js
document.addEventListener("DOMContentLoaded", function () {
  const parte1 = document.getElementById("parte1");
  const parte2 = document.getElementById("parte2");
  const btnProximo = document.getElementById("btnProximo");
  const btnVoltar = document.getElementById("btnVoltar");
  const cepInput = document.getElementById("cep");

  if (btnProximo) {
    btnProximo.addEventListener("click", () => {
      const nome = document.getElementById("nome_empresa").value.trim();
      const cnpj = document.getElementById("cnpj").value.trim();
      const email = document.getElementById("email").value.trim();
      const senha = document.getElementById("senha").value.trim();
      const cSenha = document.getElementById("cSenha").value.trim();

      // // validação
      // if (!nome || !cnpj || !email || !telefone || !senha || !cSenha) {
      //   alert("Por favor, preencha todos os campos antes de prosseguir.");
      //   return;
      // }

      // if (senha !== cSenha) {
      //   alert("As senhas não coincidem!");
      //   return;
      // }

      parte1.style.opacity = "0";
      setTimeout(() => {
        parte1.style.display = "none";
        parte2.style.display = "block";
        parte2.style.opacity = "0";
        setTimeout(() => (parte2.style.opacity = "1"), 100);
      }, 200);
    });
  }

  if (btnVoltar) {
    btnVoltar.addEventListener("click", () => {
      parte2.style.opacity = "0";
      setTimeout(() => {
        parte2.style.display = "none";
        parte1.style.display = "block";
        parte1.style.opacity = "0";
        setTimeout(() => (parte1.style.opacity = "1"), 100);
      }, 200);
    });
  }

  // Uma API publica do "brasilapi.com.br" que usa o cep para completar as informações
  // da endereço, bairro, cidade e uf.

  if (cepInput) {
    cepInput.addEventListener("input", function () {
      let cep = this.value.replace(/\D/g, "");
      if (cep.length > 5) cep = cep.replace(/(\d{5})(\d)/, "$1-$2");
      this.value = cep;
    });

    cepInput.addEventListener("input", async function () {
      const cep = this.value.replace(/\D/g, "");

      if (cep.length !== 8) return;

      try {
        const response = await fetch(`https://brasilapi.com.br/api/cep/v2/${cep}`);
        const data = await response.json();

        if (data.error) {
          alert("CEP não encontrado!");
          return;
        }

        document.getElementById("endereco").value = data.street || "";
        document.getElementById("bairro").value = data.neighborhood || "";
        document.getElementById("cidade").value = data.city || "";
        document.getElementById("uf").value = data.state || "";


      } catch (error) {
        console.error("Erro ao buscar CEP:", error);
        alert("Erro ao consultar o CEP. Tente novamente.");
      }
    });
  }
});