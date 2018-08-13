package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Person;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:27
 */
public interface PersonDao {

    /**
     * 查询所有人员
     *
     * @return list of person
     */
    List<Person> findAll();

    /**
     * 根据person id
     *
     * @param personId person id
     * @return person
     */
    Person findByPersonId(Integer personId);

    /**
     * 根据卡号查询
     *
     * @param cardNo 考好
     * @return person
     */
    Person findByCardNo(String cardNo);
}
