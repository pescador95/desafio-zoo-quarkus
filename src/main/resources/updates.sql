update historicoclinico set nomeanimal = animal.nomeapelido from animal where historicoclinico.animalid = animal.id;
update historicoetologico set nomeanimal = animal.nomeapelido from animal where historicoetologico.animalid = animal.id;
update enriquecimentoambiental set nomeanimal = animal.nomeapelido from animal where enriquecimentoambiental.animalid = animal.id;
update nutricao set nomeanimal = animal.nomeapelido from animal where nutricao.animalid = animal.id;
update usuario set usuario = usuario.nome;
update animal set idade = 'Filhote' where id between 1 and 333;
update animal set idade = 'Adulto' where id between 334 and 667;
update animal set idade = 'Idoso' where id between 668 and 1000;
update animal set sexo = 'Macho' where sexo = 'Male';
update animal set sexo = 'Fêmea' where sexo = 'Female';
SELECT setval('animal_id_seq', (select MAX(id) from animal));
SELECT setval('enriquecimentoambiental_id_seq', (select MAX(id) from enriquecimentoambiental));
SELECT setval('historicoclinico_id_seq', (select MAX(id) from historicoclinico));
SELECT setval('historicoetologico_id_seq', (select MAX(id) from historicoetologico));
SELECT setval('medicacao_id_seq', (select MAX(id) from medicacao));
SELECT setval('nutricao_id_seq', (select MAX(id) from nutricao));
SELECT setval('usuario_id_seq', (select MAX(id) from usuario));

