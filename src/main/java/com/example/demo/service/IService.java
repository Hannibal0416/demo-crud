package com.example.demo.service;

import java.util.List;

public interface IService<REQ, RES> {
  List<RES> getAll(Integer offset, Integer limit);

  RES getById(Long id);

  Long add(REQ req);

  void update(Long id, REQ req);

  void delete(Long id);
}
