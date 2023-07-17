package com.project.service;

import com.project.entity.CartFurnitureEntity;
import com.project.repository.CartFurnitureRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class CartFurnitureServiceImpl implements CartFurnitureService {

    @Autowired
    CartFurnitureRepository cartFurnitureRepository;

    @Override
    public void assign(CartFurnitureEntity cartFurnitureEntity) {

        cartFurnitureRepository.save(cartFurnitureEntity);
    }

    @Override
    public List<CartFurnitureEntity> getlist() {
        return cartFurnitureRepository.findAll();
    }

    @Override
    public void delete(Long cfid) {
//        Long delCfid = cartFurnitureRepository.findCFID(cfid);
        cartFurnitureRepository.deleteById(cfid);
    }

    @Override
    public CartFurnitureEntity search(Long cfid) {
        return cartFurnitureRepository.findByCfid(cfid);
    }

    @Override
    public CartFurnitureEntity findFidAndAmount(Long cfid) { return cartFurnitureRepository.selectFidAndAmount(cfid);}

    @Override
    public List<CartFurnitureEntity> findCF(Long cid) {
        return cartFurnitureRepository.findAllByCid(cid);
    }

    @Override
    public Long findFid(Long cfid) {
        log.info("service cartfurniture");
        log.info(cfid);
        return cartFurnitureRepository.selectFid(cfid);
    }

    @Override
    public Long findcfid(Long fid) {
        return cartFurnitureRepository.selectCfid(fid);
    }

    @Override
    public CartFurnitureEntity isitExists(Long fid, Long cid) {
        return cartFurnitureRepository.findAnyCF(fid, cid);
    }

    @Transactional
    @Override
    public void updateAmount(Long count, Long cid) {
        cartFurnitureRepository.updateAmounts(count, cid);
    }

    @Transactional
    @Override
    public void deleteByFid(Long fid) {
        cartFurnitureRepository.deleteByFid(fid);
    }

//    @Override
//    public CartFurniture selectFK(Long cfid) {
//        return cartFurnitureRepository.findByCartFurniture_Cfid(cfid);
//    }
}
