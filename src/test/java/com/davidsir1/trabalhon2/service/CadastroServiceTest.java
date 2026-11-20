package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Raca;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CadastroServiceTest {
    @Test
    void rejeitaNomeVazioDeEspecie() {
        EspecieRacaService service = new EspecieRacaService();

        assertThrows(DadosInvalidosException.class, () -> service.cadastrarEspecie("   "));
    }

    @Test
    void rejeitaNomeVazioDeRaca() {
        EspecieRacaService service = new EspecieRacaService();

        assertThrows(DadosInvalidosException.class, () -> service.cadastrarRaca("", null));
    }

    @Test
    void rejeitaCapacidadeNaoPositiva() {
        BaiaService service = new BaiaService();

        assertThrows(DadosInvalidosException.class, () -> service.cadastrarBaia("B-01", 0));
    }

    @Test
    void rejeitaAlocacaoSemAnimalOuBaia() {
        BaiaService service = new BaiaService();
        Animal animal = new Animal(1, "Toby", "Médio", "M", "DISPONIVEL", new Raca());

        assertThrows(DadosInvalidosException.class, () -> service.alocarAnimal(null, null));
        assertThrows(DadosInvalidosException.class, () -> service.alocarAnimal(animal, null));
    }
}
