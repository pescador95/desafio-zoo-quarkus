INSERT
INTO
  usuario
  (id, nome, password, email, isAtivo, dataAcao, roleUsuario)
VALUES
  (nextval('usuario_id_seq'), 'Iédio João Carabolante Júnior', '$2a$10$o8Ny8k.LIjXFQAGv/ixbOe72IYrJxL6c.AcoU2ksbzNl4Mk8LvM2e', 'iedio_junior@hotmail.com', TRUE, NOW(),'dev');

  INSERT
  INTO
    usuario
    (id, nome, password, email, isAtivo, dataAcao, roleUsuario)
  VALUES
    (nextval('usuario_id_seq'), 'Adrisson Vinicius Araújo', '$2a$10$o8Ny8k.LIjXFQAGv/ixbOe72IYrJxL6c.AcoU2ksbzNl4Mk8LvM2e', 'adrissonvinicius02@gmail.com', TRUE, NOW(), 'dev');

    INSERT
INTO
  usuario
  (id, nome, password, email, isAtivo, dataAcao, roleUsuario)
VALUES
  (nextval('usuario_id_seq'), 'Felipe Folmer Cecatto', '$2a$10$o8Ny8k.LIjXFQAGv/ixbOe72IYrJxL6c.AcoU2ksbzNl4Mk8LvM2e', 'ffcecatto@minha.fag.edu.br', TRUE, NOW(), 'dev');
