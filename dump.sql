--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.10
-- Dumped by pg_dump version 10.5 (Ubuntu 10.5-1.pgdg18.04+1)

-- Started on 2018-10-04 10:13:20 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2330 (class 0 OID 0)
-- Dependencies: 2329
-- Name: DATABASE ponto_producao; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE ponto_producao IS 'ponto rails';


--
-- TOC entry 7 (class 2615 OID 17215)
-- Name: adm_ponto; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA adm_ponto;


ALTER SCHEMA adm_ponto OWNER TO postgres;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2332 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 17216)
-- Name: administrador_areas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.administrador_areas (
    id integer NOT NULL,
    colaborador_id integer,
    area_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.administrador_areas OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 17219)
-- Name: administrador_areas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.administrador_areas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.administrador_areas_id_seq OWNER TO postgres;

--
-- TOC entry 2333 (class 0 OID 0)
-- Dependencies: 187
-- Name: administrador_areas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.administrador_areas_id_seq OWNED BY adm_ponto.administrador_areas.id;


--
-- TOC entry 188 (class 1259 OID 17221)
-- Name: ajustes; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.ajustes (
    id integer NOT NULL,
    colaborador_id integer,
    data timestamp without time zone,
    motivo character varying,
    status character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    hora time without time zone,
    observacao character varying,
    data_atendimento date
);


ALTER TABLE adm_ponto.ajustes OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 17227)
-- Name: ajustes_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.ajustes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.ajustes_id_seq OWNER TO postgres;

--
-- TOC entry 2334 (class 0 OID 0)
-- Dependencies: 189
-- Name: ajustes_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.ajustes_id_seq OWNED BY adm_ponto.ajustes.id;


--
-- TOC entry 190 (class 1259 OID 17229)
-- Name: ar_internal_metadata; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.ar_internal_metadata (
    key character varying NOT NULL,
    value character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.ar_internal_metadata OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 17235)
-- Name: areas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.areas (
    id integer NOT NULL,
    nome character varying,
    sigla character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.areas OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 17241)
-- Name: areas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.areas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.areas_id_seq OWNER TO postgres;

--
-- TOC entry 2335 (class 0 OID 0)
-- Dependencies: 192
-- Name: areas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.areas_id_seq OWNED BY adm_ponto.areas.id;


--
-- TOC entry 193 (class 1259 OID 17243)
-- Name: colaboradores; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.colaboradores (
    id integer NOT NULL,
    nome character varying,
    cpf character varying,
    area_id integer,
    role character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    grade_id integer,
    password_digest character varying,
    email character varying,
    ativo boolean DEFAULT true,
    desvinculado boolean DEFAULT false,
    data_desvinculacao date,
    subarea_id integer,
    digitaldireita bytea,
    digitalesquerda bytea
);


ALTER TABLE adm_ponto.colaboradores OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 17251)
-- Name: colaboradores_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.colaboradores_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.colaboradores_id_seq OWNER TO postgres;

--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 194
-- Name: colaboradores_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.colaboradores_id_seq OWNED BY adm_ponto.colaboradores.id;


--
-- TOC entry 195 (class 1259 OID 17253)
-- Name: dia_grades; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.dia_grades (
    id integer NOT NULL,
    dia integer,
    entrada_manha time without time zone,
    saida_almoco time without time zone,
    entrada_almoco time without time zone,
    saida_tarde time without time zone,
    grade_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.dia_grades OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 17256)
-- Name: dia_grades_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.dia_grades_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.dia_grades_id_seq OWNER TO postgres;

--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 196
-- Name: dia_grades_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.dia_grades_id_seq OWNED BY adm_ponto.dia_grades.id;


--
-- TOC entry 197 (class 1259 OID 17258)
-- Name: dias_facultativos; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.dias_facultativos (
    id integer NOT NULL,
    data date,
    horas_a_registrar integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.dias_facultativos OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17261)
-- Name: dias_facultativos_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.dias_facultativos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.dias_facultativos_id_seq OWNER TO postgres;

--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 198
-- Name: dias_facultativos_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.dias_facultativos_id_seq OWNED BY adm_ponto.dias_facultativos.id;


--
-- TOC entry 199 (class 1259 OID 17263)
-- Name: feriados; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.feriados (
    id integer NOT NULL,
    nome character varying NOT NULL,
    data date NOT NULL,
    fixo boolean NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.feriados OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17269)
-- Name: feriados_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.feriados_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.feriados_id_seq OWNER TO postgres;

--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 200
-- Name: feriados_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.feriados_id_seq OWNED BY adm_ponto.feriados.id;


--
-- TOC entry 201 (class 1259 OID 17271)
-- Name: ferias; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.ferias (
    id integer NOT NULL,
    data_inicial date,
    data_final date,
    colaborador_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    status character varying,
    observacao character varying,
    data_atendimento timestamp without time zone
);


ALTER TABLE adm_ponto.ferias OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 17277)
-- Name: ferias_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.ferias_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.ferias_id_seq OWNER TO postgres;

--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 202
-- Name: ferias_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.ferias_id_seq OWNED BY adm_ponto.ferias.id;


--
-- TOC entry 203 (class 1259 OID 17279)
-- Name: grades; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.grades (
    id integer NOT NULL,
    nome character varying,
    tolerancia integer,
    horas_diarias integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    restringir_horarios boolean DEFAULT false,
    tolerancia_periodo integer DEFAULT 250,
    restringir_periodo boolean DEFAULT true
);


ALTER TABLE adm_ponto.grades OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 17288)
-- Name: grades_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.grades_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.grades_id_seq OWNER TO postgres;

--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 204
-- Name: grades_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.grades_id_seq OWNED BY adm_ponto.grades.id;


--
-- TOC entry 205 (class 1259 OID 17290)
-- Name: ip_permitidos; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.ip_permitidos (
    id integer NOT NULL,
    ip character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    nome_maquina character varying
);


ALTER TABLE adm_ponto.ip_permitidos OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 17296)
-- Name: ip_permitidos_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.ip_permitidos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.ip_permitidos_id_seq OWNER TO postgres;

--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 206
-- Name: ip_permitidos_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.ip_permitidos_id_seq OWNED BY adm_ponto.ip_permitidos.id;


--
-- TOC entry 207 (class 1259 OID 17298)
-- Name: justificativas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.justificativas (
    id integer NOT NULL,
    quantidade_horas time without time zone,
    motivo text,
    data date,
    anexo character varying,
    colaborador_id integer,
    status character varying,
    observacao text,
    data_atendimento timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    tipo_justificativa_id integer,
    produtividade boolean
);


ALTER TABLE adm_ponto.justificativas OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 17304)
-- Name: justificativas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.justificativas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.justificativas_id_seq OWNER TO postgres;

--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 208
-- Name: justificativas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.justificativas_id_seq OWNED BY adm_ponto.justificativas.id;


--
-- TOC entry 209 (class 1259 OID 17306)
-- Name: lider_subareas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.lider_subareas (
    id integer NOT NULL,
    colaborador_id integer,
    subarea_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.lider_subareas OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 17309)
-- Name: lider_subareas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.lider_subareas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.lider_subareas_id_seq OWNER TO postgres;

--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 210
-- Name: lider_subareas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.lider_subareas_id_seq OWNED BY adm_ponto.lider_subareas.id;


--
-- TOC entry 211 (class 1259 OID 17311)
-- Name: meta_indicador_presencas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.meta_indicador_presencas (
    id integer NOT NULL,
    percentagem integer,
    area_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.meta_indicador_presencas OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 17314)
-- Name: meta_indicador_presencas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.meta_indicador_presencas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.meta_indicador_presencas_id_seq OWNER TO postgres;

--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 212
-- Name: meta_indicador_presencas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.meta_indicador_presencas_id_seq OWNED BY adm_ponto.meta_indicador_presencas.id;


--
-- TOC entry 213 (class 1259 OID 17316)
-- Name: ponto_invalidos; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.ponto_invalidos (
    id integer NOT NULL,
    colaborador_id integer,
    data timestamp without time zone,
    resolvido boolean,
    tolerancia_periodo boolean,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.ponto_invalidos OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 17319)
-- Name: ponto_invalidos_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.ponto_invalidos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.ponto_invalidos_id_seq OWNER TO postgres;

--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 214
-- Name: ponto_invalidos_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.ponto_invalidos_id_seq OWNED BY adm_ponto.ponto_invalidos.id;


--
-- TOC entry 215 (class 1259 OID 17321)
-- Name: pontos; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.pontos (
    id integer NOT NULL,
    colaborador_id integer,
    data timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    migrado boolean DEFAULT false
);


ALTER TABLE adm_ponto.pontos OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17324)
-- Name: pontos_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.pontos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.pontos_id_seq OWNER TO postgres;

--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 216
-- Name: pontos_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.pontos_id_seq OWNED BY adm_ponto.pontos.id;


--
-- TOC entry 217 (class 1259 OID 17326)
-- Name: schema_migrations; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.schema_migrations (
    version character varying NOT NULL
);


ALTER TABLE adm_ponto.schema_migrations OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17332)
-- Name: subareas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.subareas (
    id integer NOT NULL,
    nome character varying,
    area_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE adm_ponto.subareas OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17338)
-- Name: subareas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.subareas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.subareas_id_seq OWNER TO postgres;

--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 219
-- Name: subareas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.subareas_id_seq OWNED BY adm_ponto.subareas.id;


--
-- TOC entry 220 (class 1259 OID 17340)
-- Name: tipo_justificativas; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.tipo_justificativas (
    id integer NOT NULL,
    nome character varying,
    presenca boolean,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    ativo boolean
);


ALTER TABLE adm_ponto.tipo_justificativas OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17346)
-- Name: tipo_justificativas_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.tipo_justificativas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.tipo_justificativas_id_seq OWNER TO postgres;

--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 221
-- Name: tipo_justificativas_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.tipo_justificativas_id_seq OWNED BY adm_ponto.tipo_justificativas.id;


--
-- TOC entry 222 (class 1259 OID 17348)
-- Name: versions; Type: TABLE; Schema: adm_ponto; Owner: postgres
--

CREATE TABLE adm_ponto.versions (
    id integer NOT NULL,
    item_type character varying NOT NULL,
    item_id integer NOT NULL,
    event character varying NOT NULL,
    whodunnit character varying,
    object text,
    created_at timestamp without time zone
);


ALTER TABLE adm_ponto.versions OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17354)
-- Name: versions_id_seq; Type: SEQUENCE; Schema: adm_ponto; Owner: postgres
--

CREATE SEQUENCE adm_ponto.versions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adm_ponto.versions_id_seq OWNER TO postgres;

--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 223
-- Name: versions_id_seq; Type: SEQUENCE OWNED BY; Schema: adm_ponto; Owner: postgres
--

ALTER SEQUENCE adm_ponto.versions_id_seq OWNED BY adm_ponto.versions.id;


--
-- TOC entry 2125 (class 2604 OID 17356)
-- Name: administrador_areas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.administrador_areas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.administrador_areas_id_seq'::regclass);


--
-- TOC entry 2126 (class 2604 OID 17357)
-- Name: ajustes id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ajustes ALTER COLUMN id SET DEFAULT nextval('adm_ponto.ajustes_id_seq'::regclass);


--
-- TOC entry 2127 (class 2604 OID 17358)
-- Name: areas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.areas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.areas_id_seq'::regclass);


--
-- TOC entry 2130 (class 2604 OID 17359)
-- Name: colaboradores id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.colaboradores ALTER COLUMN id SET DEFAULT nextval('adm_ponto.colaboradores_id_seq'::regclass);


--
-- TOC entry 2131 (class 2604 OID 17360)
-- Name: dia_grades id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.dia_grades ALTER COLUMN id SET DEFAULT nextval('adm_ponto.dia_grades_id_seq'::regclass);


--
-- TOC entry 2132 (class 2604 OID 17361)
-- Name: dias_facultativos id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.dias_facultativos ALTER COLUMN id SET DEFAULT nextval('adm_ponto.dias_facultativos_id_seq'::regclass);


--
-- TOC entry 2133 (class 2604 OID 17362)
-- Name: feriados id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.feriados ALTER COLUMN id SET DEFAULT nextval('adm_ponto.feriados_id_seq'::regclass);


--
-- TOC entry 2134 (class 2604 OID 17363)
-- Name: ferias id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ferias ALTER COLUMN id SET DEFAULT nextval('adm_ponto.ferias_id_seq'::regclass);


--
-- TOC entry 2138 (class 2604 OID 17364)
-- Name: grades id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.grades ALTER COLUMN id SET DEFAULT nextval('adm_ponto.grades_id_seq'::regclass);


--
-- TOC entry 2139 (class 2604 OID 17365)
-- Name: ip_permitidos id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ip_permitidos ALTER COLUMN id SET DEFAULT nextval('adm_ponto.ip_permitidos_id_seq'::regclass);


--
-- TOC entry 2140 (class 2604 OID 17366)
-- Name: justificativas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.justificativas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.justificativas_id_seq'::regclass);


--
-- TOC entry 2141 (class 2604 OID 17367)
-- Name: lider_subareas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.lider_subareas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.lider_subareas_id_seq'::regclass);


--
-- TOC entry 2142 (class 2604 OID 17368)
-- Name: meta_indicador_presencas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.meta_indicador_presencas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.meta_indicador_presencas_id_seq'::regclass);


--
-- TOC entry 2143 (class 2604 OID 17369)
-- Name: ponto_invalidos id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ponto_invalidos ALTER COLUMN id SET DEFAULT nextval('adm_ponto.ponto_invalidos_id_seq'::regclass);


--
-- TOC entry 2144 (class 2604 OID 17370)
-- Name: pontos id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.pontos ALTER COLUMN id SET DEFAULT nextval('adm_ponto.pontos_id_seq'::regclass);


--
-- TOC entry 2146 (class 2604 OID 17371)
-- Name: subareas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.subareas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.subareas_id_seq'::regclass);


--
-- TOC entry 2147 (class 2604 OID 17372)
-- Name: tipo_justificativas id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.tipo_justificativas ALTER COLUMN id SET DEFAULT nextval('adm_ponto.tipo_justificativas_id_seq'::regclass);


--
-- TOC entry 2148 (class 2604 OID 17373)
-- Name: versions id; Type: DEFAULT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.versions ALTER COLUMN id SET DEFAULT nextval('adm_ponto.versions_id_seq'::regclass);


--
-- TOC entry 2150 (class 2606 OID 17375)
-- Name: administrador_areas administrador_areas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.administrador_areas
    ADD CONSTRAINT administrador_areas_pkey PRIMARY KEY (id);


--
-- TOC entry 2154 (class 2606 OID 17377)
-- Name: ajustes ajustes_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ajustes
    ADD CONSTRAINT ajustes_pkey PRIMARY KEY (id);


--
-- TOC entry 2156 (class 2606 OID 17379)
-- Name: ar_internal_metadata ar_internal_metadata_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ar_internal_metadata
    ADD CONSTRAINT ar_internal_metadata_pkey PRIMARY KEY (key);


--
-- TOC entry 2158 (class 2606 OID 17381)
-- Name: areas areas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.areas
    ADD CONSTRAINT areas_pkey PRIMARY KEY (id);


--
-- TOC entry 2160 (class 2606 OID 17383)
-- Name: colaboradores colaboradores_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.colaboradores
    ADD CONSTRAINT colaboradores_pkey PRIMARY KEY (id);


--
-- TOC entry 2163 (class 2606 OID 17385)
-- Name: dia_grades dia_grades_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.dia_grades
    ADD CONSTRAINT dia_grades_pkey PRIMARY KEY (id);


--
-- TOC entry 2165 (class 2606 OID 17387)
-- Name: dias_facultativos dias_facultativos_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.dias_facultativos
    ADD CONSTRAINT dias_facultativos_pkey PRIMARY KEY (id);


--
-- TOC entry 2167 (class 2606 OID 17389)
-- Name: feriados feriados_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.feriados
    ADD CONSTRAINT feriados_pkey PRIMARY KEY (id);


--
-- TOC entry 2169 (class 2606 OID 17391)
-- Name: ferias ferias_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ferias
    ADD CONSTRAINT ferias_pkey PRIMARY KEY (id);


--
-- TOC entry 2172 (class 2606 OID 17393)
-- Name: grades grades_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.grades
    ADD CONSTRAINT grades_pkey PRIMARY KEY (id);


--
-- TOC entry 2174 (class 2606 OID 17395)
-- Name: ip_permitidos ip_permitidos_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ip_permitidos
    ADD CONSTRAINT ip_permitidos_pkey PRIMARY KEY (id);


--
-- TOC entry 2176 (class 2606 OID 17397)
-- Name: justificativas justificativas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.justificativas
    ADD CONSTRAINT justificativas_pkey PRIMARY KEY (id);


--
-- TOC entry 2180 (class 2606 OID 17399)
-- Name: lider_subareas lider_subareas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.lider_subareas
    ADD CONSTRAINT lider_subareas_pkey PRIMARY KEY (id);


--
-- TOC entry 2183 (class 2606 OID 17401)
-- Name: meta_indicador_presencas meta_indicador_presencas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.meta_indicador_presencas
    ADD CONSTRAINT meta_indicador_presencas_pkey PRIMARY KEY (id);


--
-- TOC entry 2185 (class 2606 OID 17403)
-- Name: ponto_invalidos ponto_invalidos_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ponto_invalidos
    ADD CONSTRAINT ponto_invalidos_pkey PRIMARY KEY (id);


--
-- TOC entry 2187 (class 2606 OID 17405)
-- Name: pontos pontos_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.pontos
    ADD CONSTRAINT pontos_pkey PRIMARY KEY (id);


--
-- TOC entry 2189 (class 2606 OID 17407)
-- Name: schema_migrations schema_migrations_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.schema_migrations
    ADD CONSTRAINT schema_migrations_pkey PRIMARY KEY (version);


--
-- TOC entry 2192 (class 2606 OID 17409)
-- Name: subareas subareas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.subareas
    ADD CONSTRAINT subareas_pkey PRIMARY KEY (id);


--
-- TOC entry 2194 (class 2606 OID 17411)
-- Name: tipo_justificativas tipo_justificativas_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.tipo_justificativas
    ADD CONSTRAINT tipo_justificativas_pkey PRIMARY KEY (id);


--
-- TOC entry 2197 (class 2606 OID 17413)
-- Name: versions versions_pkey; Type: CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.versions
    ADD CONSTRAINT versions_pkey PRIMARY KEY (id);


--
-- TOC entry 2151 (class 1259 OID 17414)
-- Name: index_administrador_areas_on_area_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_administrador_areas_on_area_id ON adm_ponto.administrador_areas USING btree (area_id);


--
-- TOC entry 2152 (class 1259 OID 17415)
-- Name: index_administrador_areas_on_colaborador_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_administrador_areas_on_colaborador_id ON adm_ponto.administrador_areas USING btree (colaborador_id);


--
-- TOC entry 2161 (class 1259 OID 17416)
-- Name: index_colaboradores_on_subarea_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_colaboradores_on_subarea_id ON adm_ponto.colaboradores USING btree (subarea_id);


--
-- TOC entry 2170 (class 1259 OID 17417)
-- Name: index_ferias_on_colaborador_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_ferias_on_colaborador_id ON adm_ponto.ferias USING btree (colaborador_id);


--
-- TOC entry 2177 (class 1259 OID 17418)
-- Name: index_lider_subareas_on_colaborador_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_lider_subareas_on_colaborador_id ON adm_ponto.lider_subareas USING btree (colaborador_id);


--
-- TOC entry 2178 (class 1259 OID 17419)
-- Name: index_lider_subareas_on_subarea_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_lider_subareas_on_subarea_id ON adm_ponto.lider_subareas USING btree (subarea_id);


--
-- TOC entry 2181 (class 1259 OID 17420)
-- Name: index_meta_indicador_presencas_on_area_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_meta_indicador_presencas_on_area_id ON adm_ponto.meta_indicador_presencas USING btree (area_id);


--
-- TOC entry 2190 (class 1259 OID 17421)
-- Name: index_subareas_on_area_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_subareas_on_area_id ON adm_ponto.subareas USING btree (area_id);


--
-- TOC entry 2195 (class 1259 OID 17422)
-- Name: index_versions_on_item_type_and_item_id; Type: INDEX; Schema: adm_ponto; Owner: postgres
--

CREATE INDEX index_versions_on_item_type_and_item_id ON adm_ponto.versions USING btree (item_type, item_id);


--
-- TOC entry 2198 (class 2606 OID 17423)
-- Name: administrador_areas fk_rails_09074f6581; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.administrador_areas
    ADD CONSTRAINT fk_rails_09074f6581 FOREIGN KEY (colaborador_id) REFERENCES adm_ponto.colaboradores(id);


--
-- TOC entry 2200 (class 2606 OID 17428)
-- Name: colaboradores fk_rails_0ef1b3557c; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.colaboradores
    ADD CONSTRAINT fk_rails_0ef1b3557c FOREIGN KEY (subarea_id) REFERENCES adm_ponto.subareas(id);


--
-- TOC entry 2199 (class 2606 OID 17433)
-- Name: administrador_areas fk_rails_1e58f74838; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.administrador_areas
    ADD CONSTRAINT fk_rails_1e58f74838 FOREIGN KEY (area_id) REFERENCES adm_ponto.areas(id);


--
-- TOC entry 2205 (class 2606 OID 17438)
-- Name: meta_indicador_presencas fk_rails_418f676a2b; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.meta_indicador_presencas
    ADD CONSTRAINT fk_rails_418f676a2b FOREIGN KEY (area_id) REFERENCES adm_ponto.areas(id);


--
-- TOC entry 2203 (class 2606 OID 17443)
-- Name: lider_subareas fk_rails_45445862ae; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.lider_subareas
    ADD CONSTRAINT fk_rails_45445862ae FOREIGN KEY (colaborador_id) REFERENCES adm_ponto.colaboradores(id);


--
-- TOC entry 2202 (class 2606 OID 17448)
-- Name: ferias fk_rails_4780c85b65; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.ferias
    ADD CONSTRAINT fk_rails_4780c85b65 FOREIGN KEY (colaborador_id) REFERENCES adm_ponto.colaboradores(id);


--
-- TOC entry 2201 (class 2606 OID 17453)
-- Name: colaboradores fk_rails_6900545b35; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.colaboradores
    ADD CONSTRAINT fk_rails_6900545b35 FOREIGN KEY (area_id) REFERENCES adm_ponto.areas(id);


--
-- TOC entry 2206 (class 2606 OID 17458)
-- Name: subareas fk_rails_f36a88fed5; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.subareas
    ADD CONSTRAINT fk_rails_f36a88fed5 FOREIGN KEY (area_id) REFERENCES adm_ponto.areas(id);


--
-- TOC entry 2204 (class 2606 OID 17463)
-- Name: lider_subareas fk_rails_fae031a145; Type: FK CONSTRAINT; Schema: adm_ponto; Owner: postgres
--

ALTER TABLE ONLY adm_ponto.lider_subareas
    ADD CONSTRAINT fk_rails_fae031a145 FOREIGN KEY (subarea_id) REFERENCES adm_ponto.subareas(id);


-- Completed on 2018-10-04 10:13:20 -03

--
-- PostgreSQL database dump complete
--

