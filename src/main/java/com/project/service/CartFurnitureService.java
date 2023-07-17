package com.project.service;

import com.project.entity.CartFurnitureEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartFurnitureService {

    public void assign(CartFurnitureEntity cartFurniture);

    public List<CartFurnitureEntity> getlist();

    public void delete(Long cfid);

    public CartFurnitureEntity search(Long cfid);

    public CartFurnitureEntity findFidAndAmount(Long cfid);

    public List<CartFurnitureEntity> findCF(Long cid);

    public Long findFid(Long cfid);
//    public Long findAmount(Long cfid);

    public Long findcfid(Long fid);

    public CartFurnitureEntity isitExists(Long fid, Long cid);

    public void updateAmount(Long count, Long cid);

    public void deleteByFid(Long fid);
}
