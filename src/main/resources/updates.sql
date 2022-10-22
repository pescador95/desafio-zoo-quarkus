update historicoclinico set nomeanimal = animal.nomeapelido from animal where historicoclinico.animalid = animal.id;
update historicoetologico set nomeanimal = animal.nomeapelido from animal where historicoetologico.animalid = animal.id;
update enriquecimentoambiental set nomeanimal = animal.nomeapelido from animal where enriquecimentoambiental.animalid = animal.id;
update nutricao set nomeanimal = animal.nomeapelido from animal where nutricao.animalid = animal.id;
update usuario set usuario = usuario.nome;
update animal set idade = 'Filhote' where id between 1 and 333;
update animal set idade = 'Adulto' where id between 334 and 667;
update animal set idade = 'Idoso' where id between 668 and 1000;