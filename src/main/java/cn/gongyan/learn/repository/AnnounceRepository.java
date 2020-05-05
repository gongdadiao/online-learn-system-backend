package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Announce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnounceRepository extends JpaRepository<Announce, String> {
}
