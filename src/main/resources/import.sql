INSERT
INTO
  usuario
  (id, nome, password, email, isAtivo, dataAcao, roleUsuario)
VALUES
  (nextval('usuario_id_seq'), 'Iédio João Carabolante Júnior', '4v&Lu¢1F3r', 'iedio_junior@hotmail.com', TRUE, NOW(),'Admin');

  INSERT
  INTO
    usuario
    (id, nome, password, email, isAtivo, dataAcao, roleUsuario)
  VALUES
    (nextval('usuario_id_seq'), 'Adrisson Vinicius Araújo', '1234*', 'adrissonvinicius02@gmail.com', TRUE, NOW(), 'Admin');

    INSERT
INTO
  usuario
  (id, nome, password, email, isAtivo, dataAcao, roleUsuario)
VALUES
  (nextval('usuario_id_seq'), 'Felipe Folmer Cecatto', 'trocarsenha123', 'ffcecatto@minha.fag.edu.br', TRUE, NOW(), 'Admin');
