-- Migration: Create agents table
-- Description: Creates the agents table with UUID generation handled by the database
-- Author: Renan Resende
-- Date: 11/2025

-- Create extension for UUID generation (PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE companies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    document VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_company_slug ON companies (slug);
CREATE INDEX idx_company_status ON companies (status);
CREATE INDEX idx_company_document ON companies (document);

-- ============================
--  COMPANY SETTINGS (1:1 Shared PK)
-- ============================

CREATE TABLE company_settings (
    company_id UUID PRIMARY KEY REFERENCES companies(id) ON DELETE CASCADE,

    max_agents INT NOT NULL DEFAULT 5,
    max_queues INT NOT NULL DEFAULT 5,

    timezone VARCHAR(50) DEFAULT 'UTC' NOT NULL,
    language VARCHAR(10) DEFAULT 'en' NOT NULL,
    plan VARCHAR(50) NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);
