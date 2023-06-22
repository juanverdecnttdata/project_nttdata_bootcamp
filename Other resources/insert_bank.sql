--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-22 17:50:12

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

--
-- TOC entry 3385 (class 0 OID 16530)
-- Dependencies: 229
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (6, 1, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 3);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (7, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (8, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (9, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (10, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (11, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (12, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (13, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (14, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (15, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (18, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (19, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (20, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (21, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 15000, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (16, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 2500.00, 2);
INSERT INTO public.account (id_account, id_client, status, creation_date, creation_terminal, modification_date, modification_terminal, balance, id_product) VALUES (17, 2, 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 2);


--
-- TOC entry 3379 (class 0 OID 16481)
-- Dependencies: 223
-- Data for Name: account_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 10, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 11, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (3, 12, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (4, 13, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (5, 14, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (6, 15, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (7, 16, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (8, 17, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (9, 18, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (10, 19, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (11, 20, 2, 1, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.account_detail (id_account_detail, id_account, id_person, holder, authorized_signature, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (12, 21, 2, 1, 1, 1, '2023-06-21', '127.0.0.1', NULL, NULL);


--
-- TOC entry 3381 (class 0 OID 16491)
-- Dependencies: 225
-- Data for Name: account_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (1, 17, NULL, 1, 1, '2023-06-21', '127.0.0.1', 500.00);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (3, 16, NULL, 1, 1, '2023-06-21', '127.0.0.1', 1500.00);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (4, 16, NULL, 2, 1, '2023-06-21', '127.0.0.1', 4500.00);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (5, 16, NULL, 1, 1, '2023-06-21', '127.0.0.1', 18000);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (6, 16, NULL, 2, 1, '2023-06-21', '127.0.0.1', 2500);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (7, NULL, 3, 3, 1, '2023-06-22', '127.0.0.1', 300);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (8, NULL, 3, 4, 1, '2023-06-22', '127.0.0.1', 8000);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (9, NULL, 3, 4, 1, '2023-06-22', '127.0.0.1', 37500);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (10, 17, NULL, 4, 1, '2023-06-22', '127.0.0.1', 37500);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (11, 17, NULL, 2, 1, '2023-06-22', '127.0.0.1', 37500);
INSERT INTO public.account_history (id_account_history, id_account, id_client_product, operation_type, status, operation_date, operation_terminal, amount) VALUES (12, 17, NULL, 1, 1, '2023-06-22', '127.0.0.1', 37500);


--
-- TOC entry 3375 (class 0 OID 16451)
-- Dependencies: 219
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.client (id_client, id_person, status, id_client_type, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 1, 1, 1, '2023-06-20', '127.0.0.1', NULL, NULL);
INSERT INTO public.client (id_client, id_person, status, id_client_type, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 2, 1, 2, '2023-06-20', '127.0.0.1', NULL, NULL);


--
-- TOC entry 3387 (class 0 OID 16537)
-- Dependencies: 231
-- Data for Name: client_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 2, 2, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 2, 2, 1, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (4, 2, 2, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (5, 2, 2, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (6, 2, 2, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (7, 2, 2, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (8, 2, 4, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (9, 2, 4, NULL, 50000, 15200, 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.client_product (id_client_product, id_client, id_product, id_account, credit_limit, credit, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (3, 2, 4, NULL, 50000, 0, 1, '2023-06-21', '127.0.0.1', NULL, NULL);


--
-- TOC entry 3373 (class 0 OID 16446)
-- Dependencies: 217
-- Data for Name: client_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.client_type (id_client_type, description, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 'Personal', 1, '2023-06-20', '127.0.0.', NULL, NULL);
INSERT INTO public.client_type (id_client_type, description, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 'Empresarial', 1, '2023-06-20', '127.0.0.', NULL, NULL);


--
-- TOC entry 3389 (class 0 OID 16546)
-- Dependencies: 233
-- Data for Name: operation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.operation_type (id_operation_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 'Retiro', '', 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.operation_type (id_operation_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 'Deposito', '', 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.operation_type (id_operation_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (3, 'Pago de Credito', '', 1, '2023-06-21', '127.0.0.1', NULL, NULL);
INSERT INTO public.operation_type (id_operation_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (4, 'Cargar Consumo', '', 1, '2023-06-21', '127.0.0.1', NULL, NULL);


--
-- TOC entry 3371 (class 0 OID 16441)
-- Dependencies: 215
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (id_person, first_name, last_name, age, gender, status, start_date, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 'Juan Carlos', 'Verde Cortez', 31, 1, 1, '2023-06-20', '2023-06-20', '127.0.0.1', NULL, NULL);
INSERT INTO public.person (id_person, first_name, last_name, age, gender, status, start_date, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 'Maria Jose', 'Sanchez Correa', 23, 0, 1, '2023-06-20', '2023-06-20', '127.0.0.1', NULL, NULL);


--
-- TOC entry 3383 (class 0 OID 16511)
-- Dependencies: 227
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) VALUES (1, 1, NULL, 'Ahorro', 1, '2023-06-20', '127.0.0.1', NULL, NULL, 1, 3, 0, 0, 0, 1);
INSERT INTO public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) VALUES (2, 1, NULL, 'Cuenta Corriente', 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 0, 0, 1, 3.00, 1);
INSERT INTO public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) VALUES (3, 1, NULL, 'Plazo Fijo', 1, '2023-06-20', '127.0.0.1', NULL, NULL, 1, 0, 1, 0, 0, 1);
INSERT INTO public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) VALUES (4, 2, NULL, 'Personal', 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 0, 1, 1, 0, 1);
INSERT INTO public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) VALUES (5, 2, NULL, 'Empresarial', 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 0, 1, 1, 0, 1);
INSERT INTO public.product (id_product, id_product_type, id_product_subtype, name, status, creation_date, creation_terminal, modification_date, modification_terminal, comission_free_mainteance, n_movements, n_operation_month, free_movements, amount_maintenance, free_operation_per_month) VALUES (6, 2, NULL, 'Tarjeta de Credito', 1, '2023-06-20', '127.0.0.1', NULL, NULL, 0, 0, 1, 1, 0, 1);


--
-- TOC entry 3377 (class 0 OID 16456)
-- Dependencies: 221
-- Data for Name: product_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product_type (id_product_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (1, 'pasivo', 'Cuentas Bancarias', 1, '2023-06-20', '127.0.0.1', NULL, NULL);
INSERT INTO public.product_type (id_product_type, name, detail, status, creation_date, creation_terminal, modification_date, modification_terminal) VALUES (2, 'activo', 'Creditos', 1, '2023-06-20', '127.0.0.1', NULL, NULL);


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


-- Completed on 2023-06-22 17:50:12

--
-- PostgreSQL database dump complete
--

