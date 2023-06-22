--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-22 18:00:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 229 (class 1259 OID 16530)
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    id_account integer NOT NULL,
    id_client integer,
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30),
    balance numeric,
    id_product integer
);


ALTER TABLE public.account OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16481)
-- Name: account_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_detail (
    id_account_detail integer NOT NULL,
    id_account integer,
    id_person integer,
    holder integer,
    authorized_signature integer,
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.account_detail OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16480)
-- Name: account_detail_id_account_detail_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_detail_id_account_detail_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_detail_id_account_detail_seq OWNER TO postgres;

--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 222
-- Name: account_detail_id_account_detail_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_detail_id_account_detail_seq OWNED BY public.account_detail.id_account_detail;


--
-- TOC entry 225 (class 1259 OID 16491)
-- Name: account_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_history (
    id_account_history integer NOT NULL,
    id_account integer,
    id_client_product integer,
    operation_type integer,
    status integer,
    operation_date date,
    operation_terminal character varying(30),
    amount numeric
);


ALTER TABLE public.account_history OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16490)
-- Name: account_history_id_account_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_history_id_account_history_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_history_id_account_history_seq OWNER TO postgres;

--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 224
-- Name: account_history_id_account_history_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_history_id_account_history_seq OWNED BY public.account_history.id_account_history;


--
-- TOC entry 228 (class 1259 OID 16529)
-- Name: account_id_account_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_id_account_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_id_account_seq OWNER TO postgres;

--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 228
-- Name: account_id_account_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_id_account_seq OWNED BY public.account.id_account;


--
-- TOC entry 219 (class 1259 OID 16451)
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id_client integer NOT NULL,
    id_person integer,
    status integer,
    id_client_type integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16450)
-- Name: client_id_client_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_id_client_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_id_client_seq OWNER TO postgres;

--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 218
-- Name: client_id_client_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_client_seq OWNED BY public.client.id_client;


--
-- TOC entry 231 (class 1259 OID 16537)
-- Name: client_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_product (
    id_client_product integer NOT NULL,
    id_client integer,
    id_product integer,
    id_account integer,
    credit_limit numeric,
    credit numeric,
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.client_product OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16536)
-- Name: client_product_id_client_product_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_product_id_client_product_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_product_id_client_product_seq OWNER TO postgres;

--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 230
-- Name: client_product_id_client_product_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_product_id_client_product_seq OWNED BY public.client_product.id_client_product;


--
-- TOC entry 217 (class 1259 OID 16446)
-- Name: client_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_type (
    id_client_type integer NOT NULL,
    description character varying(50),
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.client_type OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16445)
-- Name: client_type_id_client_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_type_id_client_type_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_type_id_client_type_seq OWNER TO postgres;

--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 216
-- Name: client_type_id_client_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_type_id_client_type_seq OWNED BY public.client_type.id_client_type;


--
-- TOC entry 233 (class 1259 OID 16546)
-- Name: operation_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.operation_type (
    id_operation_type integer NOT NULL,
    name character varying(50),
    detail character varying(150),
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.operation_type OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16545)
-- Name: operation_type_id_operation_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.operation_type_id_operation_type_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.operation_type_id_operation_type_seq OWNER TO postgres;

--
-- TOC entry 3401 (class 0 OID 0)
-- Dependencies: 232
-- Name: operation_type_id_operation_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.operation_type_id_operation_type_seq OWNED BY public.operation_type.id_operation_type;


--
-- TOC entry 215 (class 1259 OID 16441)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id_person integer NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    age integer,
    gender integer,
    status integer,
    start_date date,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16440)
-- Name: person_id_person_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_person_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_person_seq OWNER TO postgres;

--
-- TOC entry 3402 (class 0 OID 0)
-- Dependencies: 214
-- Name: person_id_person_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_person_seq OWNED BY public.person.id_person;


--
-- TOC entry 227 (class 1259 OID 16511)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id_product integer NOT NULL,
    id_product_type integer,
    id_product_subtype integer,
    name character varying(250),
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30),
    comission_free_mainteance integer,
    n_movements integer,
    n_operation_month integer,
    free_movements integer,
    amount_maintenance numeric,
    free_operation_per_month integer
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16510)
-- Name: product_id_product_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_product_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_product_seq OWNER TO postgres;

--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 226
-- Name: product_id_product_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_id_product_seq OWNED BY public.product.id_product;


--
-- TOC entry 221 (class 1259 OID 16456)
-- Name: product_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_type (
    id_product_type integer NOT NULL,
    name character varying(100),
    detail character varying(250),
    status integer,
    creation_date date,
    creation_terminal character varying(30),
    modification_date date,
    modification_terminal character varying(30)
);


ALTER TABLE public.product_type OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16455)
-- Name: product_type_id_product_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_type_id_product_type_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_type_id_product_type_seq OWNER TO postgres;

--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 220
-- Name: product_type_id_product_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_type_id_product_type_seq OWNED BY public.product_type.id_product_type;


--
-- TOC entry 3225 (class 2604 OID 16533)
-- Name: account id_account; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account ALTER COLUMN id_account SET DEFAULT nextval('public.account_id_account_seq'::regclass);


--
-- TOC entry 3222 (class 2604 OID 16484)
-- Name: account_detail id_account_detail; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_detail ALTER COLUMN id_account_detail SET DEFAULT nextval('public.account_detail_id_account_detail_seq'::regclass);


--
-- TOC entry 3223 (class 2604 OID 16494)
-- Name: account_history id_account_history; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_history ALTER COLUMN id_account_history SET DEFAULT nextval('public.account_history_id_account_history_seq'::regclass);


--
-- TOC entry 3220 (class 2604 OID 16454)
-- Name: client id_client; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id_client SET DEFAULT nextval('public.client_id_client_seq'::regclass);


--
-- TOC entry 3226 (class 2604 OID 16540)
-- Name: client_product id_client_product; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_product ALTER COLUMN id_client_product SET DEFAULT nextval('public.client_product_id_client_product_seq'::regclass);


--
-- TOC entry 3219 (class 2604 OID 16449)
-- Name: client_type id_client_type; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_type ALTER COLUMN id_client_type SET DEFAULT nextval('public.client_type_id_client_type_seq'::regclass);


--
-- TOC entry 3227 (class 2604 OID 16549)
-- Name: operation_type id_operation_type; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_type ALTER COLUMN id_operation_type SET DEFAULT nextval('public.operation_type_id_operation_type_seq'::regclass);


--
-- TOC entry 3218 (class 2604 OID 16444)
-- Name: person id_person; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id_person SET DEFAULT nextval('public.person_id_person_seq'::regclass);


--
-- TOC entry 3224 (class 2604 OID 16514)
-- Name: product id_product; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN id_product SET DEFAULT nextval('public.product_id_product_seq'::regclass);


--
-- TOC entry 3221 (class 2604 OID 16459)
-- Name: product_type id_product_type; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_type ALTER COLUMN id_product_type SET DEFAULT nextval('public.product_type_id_product_type_seq'::regclass);


--
-- TOC entry 3385 (class 0 OID 16530)
-- Dependencies: 229
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) FROM stdin;
6	1	1	2023-06-20	127.0.0.1	\N	\N	0	3
7	2	1	2023-06-20	127.0.0.1	\N	\N	0	2
8	2	1	2023-06-20	127.0.0.1	\N	\N	0	2
9	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
10	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
11	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
12	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
13	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
14	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
15	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
18	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
19	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
20	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
21	2	1	2023-06-20	127.0.0.1	\N	\N	15000	2
16	2	1	2023-06-20	127.0.0.1	\N	\N	2500.00	2
17	2	1	2023-06-20	127.0.0.1	\N	\N	0	2
\.


--
-- TOC entry 3379 (class 0 OID 16481)
-- Dependencies: 223
-- Data for Name: account_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	10	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
2	11	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
3	12	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
4	13	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
5	14	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
6	15	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
7	16	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
8	17	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
9	18	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
10	19	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
11	20	2	1	0	1	2023-06-21	127.0.0.1	\N	\N
12	21	2	1	1	1	2023-06-21	127.0.0.1	\N	\N
\.


--
-- TOC entry 3381 (class 0 OID 16491)
-- Dependencies: 225
-- Data for Name: account_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) FROM stdin;
1	17	\N	1	1	2023-06-21	127.0.0.1	500.00
3	16	\N	1	1	2023-06-21	127.0.0.1	1500.00
4	16	\N	2	1	2023-06-21	127.0.0.1	4500.00
5	16	\N	1	1	2023-06-21	127.0.0.1	18000
6	16	\N	2	1	2023-06-21	127.0.0.1	2500
7	\N	3	3	1	2023-06-22	127.0.0.1	300
8	\N	3	4	1	2023-06-22	127.0.0.1	8000
9	\N	3	4	1	2023-06-22	127.0.0.1	37500
10	17	\N	4	1	2023-06-22	127.0.0.1	37500
11	17	\N	2	1	2023-06-22	127.0.0.1	37500
12	17	\N	1	1	2023-06-22	127.0.0.1	37500
\.


--
-- TOC entry 3375 (class 0 OID 16451)
-- Dependencies: 219
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (id_client, id_person, status, id_client_type, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	1	1	1	2023-06-20	127.0.0.1	\N	\N
2	2	1	2	2023-06-20	127.0.0.1	\N	\N
\.


--
-- TOC entry 3387 (class 0 OID 16537)
-- Dependencies: 231
-- Data for Name: client_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	2	2	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
2	2	2	1	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
4	2	2	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
5	2	2	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
6	2	2	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
7	2	2	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
8	2	4	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
9	2	4	\N	50000	15200	1	2023-06-21	127.0.0.1	\N	\N
3	2	4	\N	50000	0	1	2023-06-21	127.0.0.1	\N	\N
\.


--
-- TOC entry 3373 (class 0 OID 16446)
-- Dependencies: 217
-- Data for Name: client_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_type (id_client_type, description, status, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	Personal	1	2023-06-20	127.0.0.	\N	\N
2	Empresarial	1	2023-06-20	127.0.0.	\N	\N
\.


--
-- TOC entry 3389 (class 0 OID 16546)
-- Dependencies: 233
-- Data for Name: operation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.operation_type (id_operation_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	Retiro		1	2023-06-21	127.0.0.1	\N	\N
2	Deposito		1	2023-06-21	127.0.0.1	\N	\N
3	Pago de Credito		1	2023-06-21	127.0.0.1	\N	\N
4	Cargar Consumo		1	2023-06-21	127.0.0.1	\N	\N
\.


--
-- TOC entry 3371 (class 0 OID 16441)
-- Dependencies: 215
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (id_person, first_name, last_name, age, gender, status, start_date, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	Juan Carlos	Verde Cortez	31	1	1	2023-06-20	2023-06-20	127.0.0.1	\N	\N
2	Maria Jose	Sanchez Correa	23	0	1	2023-06-20	2023-06-20	127.0.0.1	\N	\N
\.


--
-- TOC entry 3383 (class 0 OID 16511)
-- Dependencies: 227
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) FROM stdin;
1	1	\N	Ahorro	1	2023-06-20	127.0.0.1	\N	\N	1	3	0	0	0	1
2	1	\N	Cuenta Corriente	1	2023-06-20	127.0.0.1	\N	\N	0	0	0	1	3.00	1
3	1	\N	Plazo Fijo	1	2023-06-20	127.0.0.1	\N	\N	1	0	1	0	0	1
4	2	\N	Personal	1	2023-06-20	127.0.0.1	\N	\N	0	0	1	1	0	1
5	2	\N	Empresarial	1	2023-06-20	127.0.0.1	\N	\N	0	0	1	1	0	1
6	2	\N	Tarjeta de Credito	1	2023-06-20	127.0.0.1	\N	\N	0	0	1	1	0	1
\.


--
-- TOC entry 3377 (class 0 OID 16456)
-- Dependencies: 221
-- Data for Name: product_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_type (id_product_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) FROM stdin;
1	pasivo	Cuentas Bancarias	1	2023-06-20	127.0.0.1	\N	\N
2	activo	Creditos	1	2023-06-20	127.0.0.1	\N	\N
\.


--
-- TOC entry 3405 (class 0 OID 0)
-- Dependencies: 222
-- Name: account_detail_id_account_detail_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_detail_id_account_detail_seq', 12, true);


--
-- TOC entry 3406 (class 0 OID 0)
-- Dependencies: 224
-- Name: account_history_id_account_history_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_history_id_account_history_seq', 12, true);


--
-- TOC entry 3407 (class 0 OID 0)
-- Dependencies: 228
-- Name: account_id_account_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_id_account_seq', 21, true);


--
-- TOC entry 3408 (class 0 OID 0)
-- Dependencies: 218
-- Name: client_id_client_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_client_seq', 2, true);


--
-- TOC entry 3409 (class 0 OID 0)
-- Dependencies: 230
-- Name: client_product_id_client_product_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_product_id_client_product_seq', 9, true);


--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 216
-- Name: client_type_id_client_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_type_id_client_type_seq', 2, true);


--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 232
-- Name: operation_type_id_operation_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.operation_type_id_operation_type_seq', 4, true);


--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 214
-- Name: person_id_person_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_person_seq', 2, true);


--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 226
-- Name: product_id_product_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_product_seq', 6, true);


--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 220
-- Name: product_type_id_product_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_type_id_product_type_seq', 6, true);


-- Completed on 2023-06-22 18:00:24

--
-- PostgreSQL database dump complete
--

