package com.gez.grill.entity;

import java.sql.Date;

public class Caipsc {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column caipsc.id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column caipsc.shoucsj
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	private Date shoucsj;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column caipsc.caip_id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	private String caipId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column caipsc.guk_id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	private String gukId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column caipsc.id
	 * 
	 * @return the value of caipsc.id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column caipsc.id
	 * 
	 * @param id
	 *            the value for caipsc.id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column caipsc.shoucsj
	 * 
	 * @return the value of caipsc.shoucsj
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public Date getShoucsj() {
		return shoucsj;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column caipsc.shoucsj
	 * 
	 * @param shoucsj
	 *            the value for caipsc.shoucsj
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public void setShoucsj(Date shoucsj) {
		this.shoucsj = shoucsj;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column caipsc.caip_id
	 * 
	 * @return the value of caipsc.caip_id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public String getCaipId() {
		return caipId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column caipsc.caip_id
	 * 
	 * @param caipId
	 *            the value for caipsc.caip_id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public void setCaipId(String caipId) {
		this.caipId = caipId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column caipsc.guk_id
	 * 
	 * @return the value of caipsc.guk_id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public String getGukId() {
		return gukId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column caipsc.guk_id
	 * 
	 * @param gukId
	 *            the value for caipsc.guk_id
	 * 
	 * @mbggenerated Mon Mar 24 11:05:58 CST 2014
	 */
	public void setGukId(String gukId) {
		this.gukId = gukId;
	}
}