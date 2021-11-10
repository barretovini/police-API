-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.6.3-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              11.3.0.6295
-- --------------------------------------------------------

SET FOREIGN_KEY_CHECKS=0;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Copiando dados para a tabela crimeapi.autopsia: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `autopsia` DISABLE KEYS */;
INSERT INTO `autopsia` (`id`, `data_autopsia`, `laudo`, `legista_id`, `vitima_id`) VALUES
	(1, '2021-09-07 21:00:00', 'Atropelamento', 2, 2);
/*!40000 ALTER TABLE `autopsia` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.crime: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `crime` DISABLE KEYS */;
INSERT INTO `crime` (`id`, `data_crime`, `descricao`, `criminoso_id`, `vitima_id`) VALUES
	(2, '2021-09-06 21:00:00', 'Furto de pão com ovo', 2, 2);
/*!40000 ALTER TABLE `crime` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.criminoso: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `criminoso` DISABLE KEYS */;
INSERT INTO `criminoso` (`id`, `cpf`, `nome`) VALUES
	(2, '031.002.644-01', 'Criminoso do crime muito maligno');
/*!40000 ALTER TABLE `criminoso` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.delegacia: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `delegacia` DISABLE KEYS */;
INSERT INTO `delegacia` (`id`, `batalhao`, `cep`, `complemento`, `estado`, `municipio`, `numero`, `rua`, `telefone`) VALUES
	(3, 'Primeiro batalhao', '79003-109', '', 'MS', 'Itanhangá Park', 36, 'Rua Sofia Melke', '(92) 3714-2678'),
	(4, 'Segundo batalhao', '79003-109', '', 'MS', 'Itanhangá Park', 36, 'Rua Sofia Melke', '(92) 3714-2678'),
	(5, 'Primeiro batalhao', '79003-109', '', 'MS', 'Itanhangá Park', 36, 'Rua Sofia Melke', '(92) 3714-2678');
/*!40000 ALTER TABLE `delegacia` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.delegado: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `delegado` DISABLE KEYS */;
INSERT INTO `delegado` (`id`, `funcional`, `nome`, `turno`, `delegacia_id`) VALUES
	(2, '1A2B3C', 'Zoberto', 'NOTURNO', 3),
	(3, '1A2B3C', 'Roberto Robertson', 'DIURNO', 3);
/*!40000 ALTER TABLE `delegado` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.legista: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `legista` DISABLE KEYS */;
INSERT INTO `legista` (`id`, `crm`, `nome`) VALUES
	(2, '382.962.737.563', 'Legista Leitor Junior');
/*!40000 ALTER TABLE `legista` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.perfil: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` (`id`, `nome_perfil`) VALUES
	(1, 'JUIZ'),
	(2, 'ADVOGADO'),
	(3, 'DELEGADO');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.policial: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `policial` DISABLE KEYS */;
INSERT INTO `policial` (`id`, `funcional`, `nome`, `patente`) VALUES
	(2, 'AABBCC34', 'Angelo', 'Capitão'),
	(3, 'AABBCC34', 'Bruno', 'Capitão');
/*!40000 ALTER TABLE `policial` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.prisao: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `prisao` DISABLE KEYS */;
INSERT INTO `prisao` (`id`, `data`, `criminoso_id`, `delegacia_id`, `delegado_id`, `policial_id`, `vitima_id`) VALUES
	(1, '2020-12-26 21:00:00', 2, 3, 3, 2, 2),
	(2, '2020-12-26 21:00:00', 2, 3, 3, 2, 2);
/*!40000 ALTER TABLE `prisao` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.usuario: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nome_usuario`, `senha`, `perfil_id`, `email`) VALUES
	(1, 'Lulay', '$2a$12$E7wV1YqhPdJUb/eJJVm.oen7FqSFdIp8Tb2DOd9icB.KhBlzEAixi', 1, NULL),
	(2, 'Bolsoy', '$2a$12$E7wV1YqhPdJUb/eJJVm.oen7FqSFdIp8Tb2DOd9icB.KhBlzEAixi', 2, NULL),
	(3, 'Ayay', '$2a$12$E7wV1YqhPdJUb/eJJVm.oen7FqSFdIp8Tb2DOd9icB.KhBlzEAixi', 3, NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Copiando dados para a tabela crimeapi.vitima: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `vitima` DISABLE KEYS */;
INSERT INTO `vitima` (`id`, `cpf`, `nome`) VALUES
	(2, '809.020.141-53', 'Vitima Vitimista Triste');
/*!40000 ALTER TABLE `vitima` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

SET FOREIGN_KEY_CHECKS=0;
