-- Migration: Create queues table
-- Description: Creates the queues table with UUID generation handled by the database
-- Author: Renan Resende
-- Date: 12/2025

-- Create extension for UUID generation (PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
       
 -- Create queues table
CREATE TABLE queues (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        company_id UUID NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        distribution_strategy VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
                        deleted_at TIMESTAMP WITH TIME ZONE,

                        -- Constraints corrigidas
                        CONSTRAINT fk_queues_company FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE RESTRICT,
                        CONSTRAINT chk_distribution_strategy CHECK (distribution_strategy IN ('LEAST_BUSY', 'ROUND_ROBIN', 'LONGEST_AVAILABLE', 'PRIORITY'))
);

CREATE UNIQUE INDEX idx_queue_name_company
    ON queues(company_id, name)
    WHERE deleted_at IS NULL;


-- Add comment to table
COMMENT ON TABLE queues IS 'Stores queues (for conversatin beetween customers and agents)';
