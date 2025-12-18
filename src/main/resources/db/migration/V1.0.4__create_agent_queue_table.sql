-- Migration: Create agent_queues join table
-- Description: Links agents and queues (N:N relationship)
-- Author: Renan Resende
-- Date: 12/2025

CREATE TABLE agent_queues (
                              agent_id UUID NOT NULL,
                              queue_id UUID NOT NULL,

    -- Metadados da relação
                              priority INT DEFAULT 1 NOT NULL, -- 1 = Alta, 2 = Média, etc.
                              added_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,

    -- Constraints de Integridade
                              PRIMARY KEY (agent_id, queue_id), -- Chave composta impede duplicidade

                              CONSTRAINT fk_aq_agent FOREIGN KEY (agent_id)
                                  REFERENCES agents(id) ON DELETE CASCADE,

                              CONSTRAINT fk_aq_queue FOREIGN KEY (queue_id)
                                  REFERENCES queues(id) ON DELETE CASCADE
);

-- Índices para performance
-- O PK já indexa (agent_id, queue_id).
-- index para o caminho inverso (buscar todos os agentes de uma fila):
CREATE INDEX idx_aq_queue ON agent_queues(queue_id);

-- Comentários
COMMENT ON TABLE agent_queues IS 'Associative table linking agents to their assigned queues (Many-to-Many)';
COMMENT ON COLUMN agent_queues.priority IS 'Priority level of the agent in this specific queue';