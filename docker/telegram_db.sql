--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

-- Started on 2024-12-23 20:39:51

DROP TABLE IF EXISTS public.user_info CASCADE;

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
-- TOC entry 214 (class 1259 OID 58052)
-- Name: user_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_info (
    phone_number character varying(255) NOT NULL,
    user_name character varying(255) NOT NULL,
    chat_id character varying(255)
);


ALTER TABLE public.user_info OWNER TO postgres;

--
-- TOC entry 3316 (class 0 OID 58052)
-- Dependencies: 214
-- Data for Name: user_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_info (phone_number, user_name, chat_id) VALUES ('79182842848', 'mattakvshi', '479601483');


--
-- TOC entry 3173 (class 2606 OID 58058)
-- Name: user_info user_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY (phone_number, user_name);


-- Completed on 2024-12-23 20:39:51

--
-- PostgreSQL database dump complete
--

