package br.com.egcservices.portaldamoda.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.classes.CategoriaLoja;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.classes.Excursao;
import br.com.egcservices.portaldamoda.classes.FotoEmpresa;

public class ConexaoHttp {

    //LINKS
    private String DIRETORIO = "http://egcservices.com.br/webservices/portaldamoda/";
    private String URL_JSON_LISTAR_EMPRESAS = DIRETORIO + "listar_empresas.php";
    private String URL_JSON_LISTAR_FOTOS_EMPRESA = DIRETORIO + "listar_fotos_empresa.php";
    private String URL_JSON_LISTAR_CAT_LOJAS = DIRETORIO + "listar_categorias.php";
    private String URL_JSON_LISTAR_LOJAS = DIRETORIO + "listar_lojas.php";
    private String URL_JSON_CARREGAR_EMPRESA = DIRETORIO + "carregar_empresa.php";
    private String URL_JSON_LISTAR_EXCURSOES = DIRETORIO + "listar_excursoes.php";

    static InputStream is = null;
    static HttpURLConnection conexao = null;

    private static HttpURLConnection conectar(String urlArquivo) throws IOException {
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setReadTimeout(10000);
        conexao.setConnectTimeout(15000);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public boolean temConexao(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public List<Empresa> listarEmpresasPorTipo(String cidadeId, String tipoEmpresa) {
        //Áreas das Variaveis
        Empresa mEmpresa;
        List<Empresa> mEmpresas = new ArrayList<>(), mEmpresasGold = new ArrayList<>(),
                mEmpresasPremium = new ArrayList<>(), mEmpresasFree = new ArrayList<>();
        try {
            conexao = conectar(URL_JSON_LISTAR_EMPRESAS + "?cidade=" + cidadeId + "&tipoempresa=" + tipoEmpresa);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conexao.getInputStream();
                JSONObject reader = new JSONObject(bytesToString(is));
                JSONArray jsonEmpresas = reader.getJSONArray("empresas");
                for (int i = 0; i < jsonEmpresas.length(); i++) {
                    JSONObject jsonEmpresa = jsonEmpresas.getJSONObject(i);
                    mEmpresa = new Empresa();
                    mEmpresa.setId(Integer.valueOf(jsonEmpresa.getString("ID")));
                    mEmpresa.setCidade_empresa_id(Integer.valueOf(jsonEmpresa.getString("CIDADE_EMPRESA_ID")));
                    mEmpresa.setTipo_empresa_id(Integer.valueOf(jsonEmpresa.getString("TIPO_EMPRESA_ID")));
                    mEmpresa.setPlano_empresa_id(Integer.valueOf(jsonEmpresa.getString("PLANO_EMPRESA_ID")));
                    if (mEmpresa.getTipo_empresa_id().equals(4))
                        mEmpresa.setCategoria_id(Integer.valueOf(jsonEmpresa.getString("CATEGORIA_LOJA_ID")));
                    mEmpresa.setNome_empresa(String.valueOf(jsonEmpresa.getString("NOME_EMPRESA")));
                    mEmpresa.setTelefone_emp(String.valueOf(jsonEmpresa.getString("TELEFONE_EMP")));
                    mEmpresa.setEndereco_emp(String.valueOf(jsonEmpresa.getString("ENDERECO_EMP")));
                    mEmpresa.setForma_pgto(String.valueOf(jsonEmpresa.getString("FORMA_PGTO")));
                    mEmpresa.setFacebook(String.valueOf(jsonEmpresa.getString("FACEBOOK")));
                    mEmpresa.setInstagram(String.valueOf(jsonEmpresa.getString("INSTAGRAM")));
                    mEmpresa.setTwitter(String.valueOf(jsonEmpresa.getString("TWITTER")));
                    mEmpresa.setSite(String.valueOf(jsonEmpresa.getString("SITE")));
                    mEmpresa.setNome_responsavel(String.valueOf(jsonEmpresa.getString("NOME_RESPONSAVEL")));
                    mEmpresa.setTelefone_responsavel(String.valueOf(jsonEmpresa.getString("TELEFONE_RESPONSAVEL")));
                    mEmpresa.setAtivo((Integer.parseInt(jsonEmpresa.getString("ATIVO")) == 1) ? true : false);
                    if (mEmpresa.getPlano_empresa_id().equals(1))
                        mEmpresasFree.add(mEmpresa);
                    else if (mEmpresa.getPlano_empresa_id().equals(2))
                        mEmpresasPremium.add(mEmpresa);
                    else if (mEmpresa.getPlano_empresa_id().equals(3))
                        mEmpresasGold.add(mEmpresa);
                }
                mEmpresas.addAll(mEmpresasGold);
                mEmpresas.addAll(mEmpresasPremium);
                mEmpresas.addAll(mEmpresasFree);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mEmpresas;
    }

    public List<FotoEmpresa> listarFotosPorEmpresas(String empresaId) {
        //Áreas das Variaveis
        FotoEmpresa mFotoEmpresa;
        List<FotoEmpresa> mFotosEmpresa = new ArrayList<>();
        try {
            conexao = conectar(URL_JSON_LISTAR_FOTOS_EMPRESA + "?empresaid=" + empresaId);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conexao.getInputStream();
                JSONObject reader = new JSONObject(bytesToString(is));
                JSONArray jsonFotosEmpresa = reader.getJSONArray("fotos_empresa");
                for (int i = 0; i < jsonFotosEmpresa.length(); i++) {
                    JSONObject jsonFotoEmpresa = jsonFotosEmpresa.getJSONObject(i);
                    mFotoEmpresa = new FotoEmpresa();
                    mFotoEmpresa.setId(Integer.valueOf(jsonFotoEmpresa.getString("ID")));
                    mFotoEmpresa.setEmpresa_id(Integer.valueOf(jsonFotoEmpresa.getString("EMPRESA_ID")));
                    mFotoEmpresa.setCaminho_img(jsonFotoEmpresa.getString("CAMINHO_IMG"));
                    mFotoEmpresa.setNome_produto(jsonFotoEmpresa.getString("NOME_PRODUTO"));
                    mFotoEmpresa.setDesc_produto(jsonFotoEmpresa.getString("DESC_PRODUTO"));
                    mFotoEmpresa.setValor_produto(jsonFotoEmpresa.getString("VALOR_PRODUTO"));
                    mFotosEmpresa.add(mFotoEmpresa);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mFotosEmpresa;
    }

    public List<CategoriaLoja> listarCategorias() {
        //Áreas das Variaveis
        CategoriaLoja mCat;
        List<CategoriaLoja> mCategorias = new ArrayList<>();
        try {
            conexao = conectar(URL_JSON_LISTAR_CAT_LOJAS);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conexao.getInputStream();
                JSONObject reader = new JSONObject(bytesToString(is));
                JSONArray jsonCategorias = reader.getJSONArray("categorias_loja");
                for (int i = 0; i < jsonCategorias.length(); i++) {
                    JSONObject jsonCat = jsonCategorias.getJSONObject(i);
                    mCat = new CategoriaLoja();
                    mCat.setId(Integer.valueOf(jsonCat.getString("ID")));
                    mCat.setCategoria_desc(jsonCat.getString("CATEGORIA_DESC"));
                    mCategorias.add(mCat);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mCategorias;
    }

    public List<Empresa> listarLojasPorCatCidTip(String catId, String cidadeId) {
        //Áreas das Variaveis
        Empresa mLoja;
        List<Empresa> mLojas = new ArrayList<>(), mEmpresasGold = new ArrayList<>(),
                mEmpresasPremium = new ArrayList<>(), mEmpresasFree = new ArrayList<>();
        try {
            conexao = conectar(URL_JSON_LISTAR_LOJAS + "?categoria=" + catId + "&cidade=" + cidadeId);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conexao.getInputStream();
                JSONObject reader = new JSONObject(bytesToString(is));
                JSONArray jsonLojas = reader.getJSONArray("lojas");
                for (int i = 0; i < jsonLojas.length(); i++) {
                    JSONObject jsonLoja = jsonLojas.getJSONObject(i);
                    mLoja = new Empresa();
                    mLoja.setId(Integer.valueOf(jsonLoja.getString("ID")));
                    mLoja.setCidade_empresa_id(Integer.valueOf(jsonLoja.getString("CIDADE_EMPRESA_ID")));
                    mLoja.setTipo_empresa_id(Integer.valueOf(jsonLoja.getString("TIPO_EMPRESA_ID")));
                    mLoja.setPlano_empresa_id(Integer.valueOf(jsonLoja.getString("PLANO_EMPRESA_ID")));
                    if (mLoja.getTipo_empresa_id().equals(4))
                        mLoja.setCategoria_id(Integer.valueOf(jsonLoja.getString("CATEGORIA_LOJA_ID")));
                    mLoja.setNome_empresa(String.valueOf(jsonLoja.getString("NOME_EMPRESA")));
                    mLoja.setTelefone_emp(String.valueOf(jsonLoja.getString("TELEFONE_EMP")));
                    mLoja.setEndereco_emp(String.valueOf(jsonLoja.getString("ENDERECO_EMP")));
                    mLoja.setForma_pgto(String.valueOf(jsonLoja.getString("FORMA_PGTO")));
                    mLoja.setFacebook(String.valueOf(jsonLoja.getString("FACEBOOK")));
                    mLoja.setInstagram(String.valueOf(jsonLoja.getString("INSTAGRAM")));
                    mLoja.setTwitter(String.valueOf(jsonLoja.getString("TWITTER")));
                    mLoja.setSite(String.valueOf(jsonLoja.getString("SITE")));
                    mLoja.setNome_responsavel(String.valueOf(jsonLoja.getString("NOME_RESPONSAVEL")));
                    mLoja.setTelefone_responsavel(String.valueOf(jsonLoja.getString("TELEFONE_RESPONSAVEL")));
                    mLoja.setAtivo((Integer.parseInt(jsonLoja.getString("ATIVO")) == 1) ? true : false);
                    if (mLoja.getPlano_empresa_id().equals(1))
                        mEmpresasFree.add(mLoja);
                    else if (mLoja.getPlano_empresa_id().equals(2))
                        mEmpresasPremium.add(mLoja);
                    else if (mLoja.getPlano_empresa_id().equals(3))
                        mEmpresasGold.add(mLoja);
                }
                mLojas.addAll(mEmpresasGold);
                mLojas.addAll(mEmpresasPremium);
                mLojas.addAll(mEmpresasFree);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mLojas;
    }

    public List<Empresa> listarEmpresasFavPorCidade(List<Integer> listaIds, String cidadeId) {
        //Áreas das Variaveis
        Empresa mEmpresa;
        List<Empresa> mEmpresas = new ArrayList<>();
        try {
            for (int j = 0; j < listaIds.size(); j++) {
                conexao = conectar(URL_JSON_CARREGAR_EMPRESA + "?cidade=" + cidadeId + "&empresa=" + listaIds.get(j).toString());
                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    is = conexao.getInputStream();
                    JSONObject reader = new JSONObject(bytesToString(is));
                    JSONArray jsonEmpresas = reader.getJSONArray("empresas");
                    for (int i = 0; i < jsonEmpresas.length(); i++) {
                        JSONObject jsonEmpresa = jsonEmpresas.getJSONObject(i);
                        mEmpresa = new Empresa();
                        mEmpresa.setId(Integer.valueOf(jsonEmpresa.getString("ID")));
                        mEmpresa.setCidade_empresa_id(Integer.valueOf(jsonEmpresa.getString("CIDADE_EMPRESA_ID")));
                        mEmpresa.setTipo_empresa_id(Integer.valueOf(jsonEmpresa.getString("TIPO_EMPRESA_ID")));
                        mEmpresa.setPlano_empresa_id(Integer.valueOf(jsonEmpresa.getString("PLANO_EMPRESA_ID")));
                        if (mEmpresa.getTipo_empresa_id().equals(4))
                            mEmpresa.setCategoria_id(Integer.valueOf(jsonEmpresa.getString("CATEGORIA_LOJA_ID")));
                        mEmpresa.setNome_empresa(String.valueOf(jsonEmpresa.getString("NOME_EMPRESA")));
                        mEmpresa.setTelefone_emp(String.valueOf(jsonEmpresa.getString("TELEFONE_EMP")));
                        mEmpresa.setEndereco_emp(String.valueOf(jsonEmpresa.getString("ENDERECO_EMP")));
                        mEmpresa.setForma_pgto(String.valueOf(jsonEmpresa.getString("FORMA_PGTO")));
                        mEmpresa.setFacebook(String.valueOf(jsonEmpresa.getString("FACEBOOK")));
                        mEmpresa.setInstagram(String.valueOf(jsonEmpresa.getString("INSTAGRAM")));
                        mEmpresa.setTwitter(String.valueOf(jsonEmpresa.getString("TWITTER")));
                        mEmpresa.setSite(String.valueOf(jsonEmpresa.getString("SITE")));
                        mEmpresa.setNome_responsavel(String.valueOf(jsonEmpresa.getString("NOME_RESPONSAVEL")));
                        mEmpresa.setTelefone_responsavel(String.valueOf(jsonEmpresa.getString("TELEFONE_RESPONSAVEL")));
                        mEmpresa.setAtivo((Integer.parseInt(jsonEmpresa.getString("ATIVO")) == 1) ? true : false);
                        mEmpresas.add(mEmpresa);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mEmpresas;
    }

    public List<Excursao> listarExcursoesPorCidade(String cidadeId) {
        //Áreas das Variaveis
        Excursao mExcursao;
        List<Excursao> mExcursoes = new ArrayList<>();
        try {
            conexao = conectar(URL_JSON_LISTAR_EXCURSOES + "?cidadedestino=" + cidadeId);// + "&tipoempresa=" + tipoEmpresa);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conexao.getInputStream();
                JSONObject reader = new JSONObject(bytesToString(is));
                JSONArray jsonExcursoes = reader.getJSONArray("excursoes");
                for (int i = 0; i < jsonExcursoes.length(); i++) {
                    JSONObject jsonExcursao = jsonExcursoes.getJSONObject(i);
                    mExcursao = new Excursao();
                    mExcursao.setId(Integer.valueOf(jsonExcursao.getString("ID")));
                    mExcursao.setOrigem(String.valueOf(jsonExcursao.getString("ORIGEM_EXCURSAO")));
                    mExcursao.setDestino(String.valueOf(jsonExcursao.getString("DESTINO_EXCURSAO")));
                    mExcursao.setData_hora(String.valueOf(jsonExcursao.getString("DATAHORA_EXCURSAO")));
                    mExcursao.setResponsavel(String.valueOf(jsonExcursao.getString("RESPONSAVEL_EXCURSAO")));
                    mExcursao.setTelefone_responsavel(String.valueOf(jsonExcursao.getString("TELEFONE_RESPONSAVEL")));
                    mExcursao.setValor(String.valueOf(jsonExcursao.getString("VALOR")));
                    mExcursao.setForma_pgto(String.valueOf(jsonExcursao.getString("FORMA_PGTO")));
                    mExcursao.setAtivo((Integer.parseInt(jsonExcursao.getString("ATIVO")) == 1) ? true : false);
                    mExcursoes.add(mExcursao);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mExcursoes;
    }

    private static String bytesToString(InputStream is) throws IOException {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesLidos;
        while ((bytesLidos = is.read(buf)) != -1) {
            buffer.write(buf, 0, bytesLidos);
        }
        return new String(buffer.toByteArray());
    }
}