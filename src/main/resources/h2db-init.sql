/*
 * Estrutura
 */

drop table if exists usuario;
create table usuario (
	id int not null primary key auto_increment,
	login varchar(100) not null,
	senha varchar(100) not null,
	nome varchar(255) not null,
	ativo tinyint not null,
	
	constraint uk_usuario_login unique (login)
);

drop table if exists tipo_registro;
create table tipo_registro (
	id tinyint not null primary key,
	descricao varchar(100) not null
);

drop table if exists registro;
create table registro (

	id bigint not null primary key auto_increment,
	registrante_id int not null,
	data_registro datetime not null,
	data_ultima_edicao datetime null,
	ultimo_editor_id int null,
	cidade varchar(100) not null,
	uf varchar(2) not null,

	-- (1 = Chuva Prevista | 2 = Chuva Realizada | 3 = Temperatura Prevista | 4 = Temperatura Realizada)
	tipo_registro_id tinyint not null,

	-- Previsões
	data_prevista datetime null,
	probabilidade_percentual_previsao float null,
	
	-- Realização
	data_realizacao datetime null,
	
	-- (FRACO | MEDIO | FORTE)
	intensidade_evento varchar(100) null,
	
	-- Somente em temperatura
	temperatura_em_celcius float null,
	
	foreign key (registrante_id) references usuario (id),
	foreign key (ultimo_editor_id) references usuario (id)
);



/*
 * Dados necessários ao setup
 */

insert into tipo_registro (id, descricao) values
	(1, 'Previsão de Chuva'), (2, 'Registro de Chuva'), (3, 'Previsão de Temperatura'), (4, 'Registro de Temperatura');

-- Admin para acesso inicial -> senha: 123
insert into usuario (login, senha, nome, ativo) values
	('admin', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Administrador Padrão', 1);



/*
 * Dados para teste
 */

insert into registro (data_registro, registrante_id, data_ultima_edicao, ultimo_editor_id, cidade, uf, tipo_registro_id,
	data_prevista, probabilidade_percentual_previsao, data_realizacao, intensidade_evento, temperatura_em_celcius)
values
	('2017-01-10 11:11:40', 1, '2017-05-02 15:12:00', 1, 'Rio de Janeiro', 'RJ', 1, '2017-05-07', 80.0, null, 'FORTE', null),
	('2017-05-04 12:15:00', 1, '2017-05-03 08:45:01', 1, 'Goiânia', 'GO', 2, null, null, '2017-04-25', 'MEDIO', null),
	('2017-05-01 11:00:00', 1, '2017-05-03 08:30:11', 1, 'Gurupi', 'TO', 3, '2017-05-22', 95.0, null, null, 31.0),
	('2017-05-11 14:00:00', 1, '2017-05-05 09:11:13', 1, 'Palmas', 'TO', 4, null, null, '2017-05-01', null, 34.0);	
