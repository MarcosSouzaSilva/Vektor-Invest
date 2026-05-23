package br.com.vektorinvest.vektorinvestbackendspring.data.mapper;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.AllStocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Stocks;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.AllStockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.StockDomain;

public class AllStocksMapper {



    public static AllStocks convert (AllStockDomain stockDomain){
        return AllStocks.builder()
                .stockCode(stockDomain.getStockCode())
                .companyName(stockDomain.getCompanyName())
                .sector(stockDomain.getSector())
                .build();
    }

}