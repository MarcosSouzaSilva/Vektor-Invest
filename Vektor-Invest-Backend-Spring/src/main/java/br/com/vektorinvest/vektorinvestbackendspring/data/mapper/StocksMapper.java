package br.com.vektorinvest.vektorinvestbackendspring.data.mapper;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.AllStocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Stocks;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.AllStockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.StockDomain;

public class StocksMapper {

    public static Stocks convert (StockDomain stockDomain){
        return Stocks.builder()
                .stock(stockDomain.getStockCode())
                .build();
    }

    public static AllStocks convertToEntity (AllStockDomain stockDomain){
        return AllStocks.builder()
                .stockCode(stockDomain.getStockCode())
                .sector(stockDomain.getSector())
                .companyName(stockDomain.getCompanyName())
                .build();
    }

}