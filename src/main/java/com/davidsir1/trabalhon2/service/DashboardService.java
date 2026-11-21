package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.model.Adocao;
import com.davidsir1.trabalhon2.repository.AdocaoRepository;
import com.davidsir1.trabalhon2.repository.DashboardRepository;

import java.util.List;

public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final AdocaoRepository adocaoRepository;

    public DashboardService() {
        this.dashboardRepository = new DashboardRepository();
        this.adocaoRepository = new AdocaoRepository();
    }

    public int getTotalAnimaisDisponiveis() {
        return dashboardRepository.contarAnimaisDisponiveis();
    }

    public int getTotalAdocoesRealizadas() {
        return dashboardRepository.contarAdocoesRealizadas();
    }

    public int getTotalAdotantesCadastrados() {
        return dashboardRepository.contarAdotantesCadastrados();
    }

    public int getTotalBaias() {
        return dashboardRepository.contarTotalBaias();
    }

    public List<Adocao> listarAdocoesRecentes() {
        return adocaoRepository.listarAdocoesRecentes();
    }
}
