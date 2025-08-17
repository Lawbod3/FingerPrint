-- data.sql
INSERT INTO finger_print (id, user_id, finger) VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '1', 'LEFT_THUMB');

INSERT INTO fingerprint_templates (fingerprint_id, template) VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', decode('BASE64_ENCODED_FINGERPRINT_DATA_HERE', 'base64'));