
CREATE TABLE job (
  id int(11) NOT NULL AUTO_INCREMENT,
  job_id varchar(100) NOT NULL,
  description varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE job_result (
  id int(11) NOT NULL AUTO_INCREMENT,
  job_id varchar(100) NOT NULL,
  job_time BIGINT NOT NULL,
  job_res varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE robot (
  id int(11) NOT NULL AUTO_INCREMENT,
  hostname varchar(100) NOT NULL,
  port varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT into robot (hostname,`port`) VALUES ("192.168.0.1",8080);
INSERT into robot (hostname,`port`) VALUES ("192.168.0.2",8080);

INSERT into job_result (job_id,job_time,job_res) VALUES ("111",1606037035000,"for test 111");
INSERT into job_result (job_id,job_time,job_res) VALUES ("222",1606037035000,"for test 222");