package com.sidianzhong.sdz.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Identity implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private String cardNum;

    private String cardFront;

    private String cardReverse;

    private String otherCard;

    private Date createTime;

    private Date lastUpdateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardReverse() {
        return cardReverse;
    }

    public void setCardReverse(String cardReverse) {
        this.cardReverse = cardReverse;
    }

    public String getOtherCard() {
        return otherCard;
    }

    public void setOtherCard(String otherCard) {
        this.otherCard = otherCard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Identity other = (Identity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCardNum() == null ? other.getCardNum() == null : this.getCardNum().equals(other.getCardNum()))
            && (this.getCardFront() == null ? other.getCardFront() == null : this.getCardFront().equals(other.getCardFront()))
            && (this.getCardReverse() == null ? other.getCardReverse() == null : this.getCardReverse().equals(other.getCardReverse()))
            && (this.getOtherCard() == null ? other.getOtherCard() == null : this.getOtherCard().equals(other.getOtherCard()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCardNum() == null) ? 0 : getCardNum().hashCode());
        result = prime * result + ((getCardFront() == null) ? 0 : getCardFront().hashCode());
        result = prime * result + ((getCardReverse() == null) ? 0 : getCardReverse().hashCode());
        result = prime * result + ((getOtherCard() == null) ? 0 : getOtherCard().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", cardNum=").append(cardNum);
        sb.append(", cardFront=").append(cardFront);
        sb.append(", cardReverse=").append(cardReverse);
        sb.append(", otherCard=").append(otherCard);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}