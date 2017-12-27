-- change entity treatment to Study

ALTER TABLE treatment RENAME study;
ALTER TABLE sample CHANGE treatment_id study_id int(11);
DROP INDEX treatment_id ON sample;
CREATE INDEX study_id ON sample (study_id);

