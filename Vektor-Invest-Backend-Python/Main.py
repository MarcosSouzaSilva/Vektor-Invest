from __future__ import annotations

import json
import logging
import os
import re
from typing import List

from flask import Flask, jsonify, request
from groq import Groq
from pydantic import BaseModel, ValidationError, Field


logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s"
)

logger = logging.getLogger(__name__)



class Settings:

    GROQ_API_KEY = os.getenv("GROQ_API_KEY")
    MODEL_NAME = "llama-3.3-70b-versatile"

    TRADING_VIEW_URL = (
        "https://br.tradingview.com/chart/?symbol=BMFBOVESPA%3A{ticker}"
    )

    MAX_TOKENS = 1200
    TEMPERATURE = 0.3

    @classmethod
    def validate(cls):

        if not cls.GROQ_API_KEY:
            raise RuntimeError("GROQ_API_KEY não configurada")


Settings.validate()



groq_client = Groq(api_key=Settings.GROQ_API_KEY)



class AnaliseFinanceira(BaseModel):

    ticker: str = "não disponível"
    empresa: str = "não disponível"

    precoAtual: str = "não disponível"
    variacaoDia: str = "não disponível"

    resumo: str = "não disponível"

    pl: str = "não disponível"
    pvp: str = "não disponível"
    dividendYield: str = "não disponível"
    roe: str = "não disponível"

    pontosPositivos: List[str] = Field(default_factory=list)
    riscos: List[str] = Field(default_factory=list)

    precoJusto: str = "não disponível"
    potencialValorizacao: str = "não disponível"

    score: str = "não disponível"
    classificacao: str = "não disponível"



class AnaliseIAService:

    @staticmethod
    def gerar_prompt(ticker: str) -> str:

        ticker = ticker.upper().strip()

        return f"""
Você é um analista financeiro profissional especializado no mercado acionário brasileiro (B3), com foco em análise fundamentalista de empresas listadas.

Sua tarefa é analisar a ação {ticker} de forma técnica, objetiva, consistente e conservadora, utilizando fundamentos financeiros, riscos estruturais e indicadores de mercado.

Fonte de referência:
{Settings.TRADING_VIEW_URL.format(ticker=ticker)}

Considere obrigatoriamente os seguintes critérios na análise:

- valuation
- dividendos
- geração de caixa
- rentabilidade
- endividamento
- riscos
- setor de atuação
- governança corporativa
- eficiência operacional
- previsibilidade do negócio
- qualidade dos lucros
- sustentabilidade financeira
- histórico recente da empresa
- confiança do mercado
- risco de diluição acionária
- dependência de eventos extraordinários
- capacidade de continuidade operacional
- riscos jurídicos e reputacionais
- consistência dos resultados
- saúde financeira de longo prazo

IMPORTANTE:

- Retorne APENAS JSON válido
- O JSON deve ser parseável com json.loads()
- Não escreva explicações, comentários ou texto fora do JSON
- Não utilize markdown
- Não adicione observações extras
- Todos os campos devem existir obrigatoriamente
- Nunca retorne campos vazios
- Utilize apenas informações coerentes com os dados disponíveis
- Não invente indicadores inexistentes
- O resumo deve conter no máximo 25 palavras
- Cada item das listas deve conter no máximo 12 palavras
- O score deve variar entre 0 e 10
- A classificação deve ser apenas:
  "COMPRA", "NEUTRO" ou "VENDA"

- Não utilize termos como:
  "empresa sólida",
  "desempenho estável",
  "crescimento consistente",
  "lucro crescente",
  "fundamentos fortes"
sem evidências claras e recentes.

- Empresas com histórico recente de crise, recuperação judicial,
prejuízos relevantes ou deterioração operacional não devem ser descritas
como estáveis ou previsíveis.

- Evite suavizar riscos relevantes na análise textual.

- O resumo deve refletir proporcionalmente os principais riscos identificados.

- Score acima de 7 deve ser reservado apenas para empresas com:
  - forte previsibilidade
  - geração consistente de caixa
  - governança robusta
  - baixo risco estrutural
  - histórico confiável

- O preço justo deve ser conservador e coerente com os fundamentos.
- Caso não exista previsibilidade suficiente, utilize valor próximo ao preço atual.
- Empresas classificadas com risco "ALTO" não devem possuir score acima de 6.
- Empresas classificadas com risco "MUITO_ALTO" não devem possuir score acima de 4.

IMPORTANTE NA CLASSIFICAÇÃO:

- Empresas com recuperação judicial, fraude contábil, forte deterioração operacional,
risco elevado de insolvência, patrimônio fragilizado ou perda severa de confiança
do mercado NÃO devem receber classificação "COMPRA" apenas por parecerem baratas.

- Valuation descontado sozinho não justifica recomendação de compra.

- Considere qualidade da gestão, credibilidade financeira e sustentabilidade do negócio.

- Empresas com elevado risco estrutural devem receber score reduzido,
mesmo com indicadores aparentemente atrativos.

- O score deve penalizar:
  - prejuízos recorrentes
  - geração de caixa fraca
  - dívida excessiva
  - diluição acionária
  - baixa previsibilidade
  - dependência de reestruturação
  - riscos jurídicos relevantes
  - governança fragilizada

- Empresas com alto risco financeiro ou operacional raramente devem possuir score acima de 6.

- A classificação deve refletir o equilíbrio entre potencial de retorno e risco estrutural da empresa.

- A classificação "COMPRA" deve ser utilizada apenas quando houver combinação consistente de:
  - fundamentos sólidos
  - previsibilidade
  - sustentabilidade financeira
  - governança confiável
  - risco controlado

Estrutura obrigatória da resposta:

{{
  "ticker": "{ticker}",
  "empresa": "Nome da empresa",

  "precoAtual": "R$ 00,00",
  "variacaoDia": "+0,00%",

  "resumo": "Resumo extremamente objetivo",

  "pl": "0.0",
  "pvp": "0.0",
  "dividendYield": "0%",
  "roe": "0%",

  "pontosPositivos": [
    "ponto 1",
    "ponto 2"
  ],

  "riscos": [
    "risco 1",
    "risco 2"
  ],

  "precoJusto": "R$ 00,00",
  "potencialValorizacao": "+0%",

  "score": "0.0 / 10",

  "classificacao": "COMPRA"
}}

Regras adicionais:

- A análise deve refletir fundamentos financeiros reais e coerentes
- A classificação deve refletir o equilíbrio entre retorno e risco estrutural
- Seja técnico, direto e profissional
- Evite linguagem emocional, promocional ou especulativa
- Priorize consistência entre score, riscos e classificação
- Empresas com riscos relevantes devem refletir isso claramente no score e classificação
"""

    @staticmethod
    def solicitar_analise(ticker: str) -> str:

        prompt = AnaliseIAService.gerar_prompt(ticker)

        logger.info("Gerando análise para ticker: %s", ticker)

        response = groq_client.chat.completions.create(
            model=Settings.MODEL_NAME,
            messages=[
                {
                    "role": "system",
                    "content": (
                        "Você é um analista financeiro profissional "
                        "especializado em ações brasileiras."
                    )
                },
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            temperature=Settings.TEMPERATURE,
            max_completion_tokens=Settings.MAX_TOKENS,
            top_p=1,
            stream=False
        )

        return response.choices[0].message.content

    @staticmethod
    def extrair_json(texto: str) -> AnaliseFinanceira:

        try:

            match = re.search(r"\{.*\}", texto, re.DOTALL)

            if not match:
                raise ValueError("JSON não encontrado")

            json_text = match.group()

            data = json.loads(json_text)

            return AnaliseFinanceira(**data)

        except (json.JSONDecodeError, ValidationError, ValueError) as ex:

            logger.error("Erro ao processar resposta da IA: %s", ex)

            return AnaliseFinanceira()

    @classmethod
    def executar(cls, ticker: str) -> AnaliseFinanceira:

        resposta = cls.solicitar_analise(ticker)

        return cls.extrair_json(resposta)


app = Flask(__name__)



@app.route("/", methods=["GET"])
def health():

    return jsonify({
        "status": "online",
        "service": "stock-analysis-api"
    })


@app.route("/analise/<ticker>", methods=["GET"])
def analisar_ticker(ticker: str):

    try:

        resultado = AnaliseIAService.executar(ticker)

        return jsonify(resultado.model_dump()), 200

    except Exception as ex:

        logger.exception("Erro interno")

        return jsonify({
            "error": "Erro interno ao processar análise",
            "details": str(ex)
        }), 500



@app.route("/analise", methods=["POST"])
def analisar_custom():

    try:

        body = request.get_json()

        if not body:
            return jsonify({
                "error": "Body JSON obrigatório"
            }), 400

        ticker = body.get("ticker")

        if not ticker:
            return jsonify({
                "error": "Campo 'ticker' é obrigatório"
            }), 400

        resultado = AnaliseIAService.executar(ticker)

        return jsonify(resultado.model_dump()), 200

    except Exception as ex:

        logger.exception("Erro interno")

        return jsonify({
            "error": "Erro interno",
            "details": str(ex)
        }), 500



if __name__ == "__main__":

    app.run(
        host="0.0.0.0",
        port=5000,
        debug=False
    )