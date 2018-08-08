package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Person;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:27
 */
public interface PersonDao {

    Person findByPersonId(Integer personId);

    Person findByCardNo(String cardNo);
}
