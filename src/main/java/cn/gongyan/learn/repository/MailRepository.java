package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, String> {
}
