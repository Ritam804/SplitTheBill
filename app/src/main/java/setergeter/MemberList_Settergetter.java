package setergeter;

/**
 * Created by ritam on 19/06/17.
 */

public class MemberList_Settergetter {


    //Category:1 for Invited,2 for Offer,3 for Confirmed

    String Name, Payment, Male, Female, Image,Category,HostId;

    String Splitter_RequesterId,Splitter_EventId,Splitter_EventName,Splitter_EventDay,Splitter_EventMonth,Splitter_EventYear,
            Splitter_EventStartTime,Splitter_EventEndTime,Splitter_OfferMale,Splitter_OfferFemale,Splitter_OfferAmount,
            Table_TableId,Table_HostName,Table_HostId,Table_TableName,Table_Cost,Table_MinAmount,Table_RamainingAmount,Table_TableStatus,
            Table_NumberOfAvailable,Table_Total,Table_WhatToDo,Splitter_Title;

    public MemberList_Settergetter(String name, String price, String male, String female, String image, String category, String hostId) {
        Name = name;
        Payment = price;
        Male = male;
        Female = female;
        Image = image;
        Category = category;
        HostId = hostId;
    }

    public MemberList_Settergetter(String name, String payment, String male, String female, String image, String category, String hostId, String splitter_RequesterId, String splitter_EventId, String splitter_EventName, String splitter_EventDay, String splitter_EventMonth, String splitter_EventYear, String splitter_EventStartTime, String splitter_EventEndTime, String splitter_OfferMale, String splitter_OfferFemale, String splitter_OfferAmount, String table_TableId, String table_HostName, String table_HostId, String table_TableName, String table_Cost, String table_MinAmount, String table_RamainingAmount, String table_TableStatus, String table_NumberOfAvailable, String table_Total, String table_WhatToDo, String title) {
        Name = name;
        Payment = payment;
        Male = male;
        Female = female;
        Image = image;
        Category = category;
        HostId = hostId;
        Splitter_RequesterId = splitter_RequesterId;
        Splitter_EventId = splitter_EventId;
        Splitter_EventName = splitter_EventName;
        Splitter_EventDay = splitter_EventDay;
        Splitter_EventMonth = splitter_EventMonth;
        Splitter_EventYear = splitter_EventYear;
        Splitter_EventStartTime = splitter_EventStartTime;
        Splitter_EventEndTime = splitter_EventEndTime;
        Splitter_OfferMale = splitter_OfferMale;
        Splitter_OfferFemale = splitter_OfferFemale;
        Splitter_OfferAmount = splitter_OfferAmount;
        Table_TableId = table_TableId;
        Table_HostName = table_HostName;
        Table_HostId = table_HostId;
        Table_TableName = table_TableName;
        Table_Cost = table_Cost;
        Table_MinAmount = table_MinAmount;
        Table_RamainingAmount = table_RamainingAmount;
        Table_TableStatus = table_TableStatus;
        Table_NumberOfAvailable = table_NumberOfAvailable;
        Table_Total = table_Total;
        Table_WhatToDo = table_WhatToDo;
        Splitter_Title = title;
    }

    public String getSplitter_Title() {
        return Splitter_Title;
    }

    public void setSplitter_Title(String splitter_Title) {
        Splitter_Title = splitter_Title;
    }

    public String getSplitter_RequesterId() {
        return Splitter_RequesterId;
    }

    public void setSplitter_RequesterId(String splitter_RequesterId) {
        Splitter_RequesterId = splitter_RequesterId;
    }

    public String getSplitter_EventId() {
        return Splitter_EventId;
    }

    public void setSplitter_EventId(String splitter_EventId) {
        Splitter_EventId = splitter_EventId;
    }

    public String getSplitter_EventName() {
        return Splitter_EventName;
    }

    public void setSplitter_EventName(String splitter_EventName) {
        Splitter_EventName = splitter_EventName;
    }

    public String getSplitter_EventDay() {
        return Splitter_EventDay;
    }

    public void setSplitter_EventDay(String splitter_EventDay) {
        Splitter_EventDay = splitter_EventDay;
    }

    public String getSplitter_EventMonth() {
        return Splitter_EventMonth;
    }

    public void setSplitter_EventMonth(String splitter_EventMonth) {
        Splitter_EventMonth = splitter_EventMonth;
    }

    public String getSplitter_EventYear() {
        return Splitter_EventYear;
    }

    public void setSplitter_EventYear(String splitter_EventYear) {
        Splitter_EventYear = splitter_EventYear;
    }

    public String getSplitter_EventStartTime() {
        return Splitter_EventStartTime;
    }

    public void setSplitter_EventStartTime(String splitter_EventStartTime) {
        Splitter_EventStartTime = splitter_EventStartTime;
    }

    public String getSplitter_EventEndTime() {
        return Splitter_EventEndTime;
    }

    public void setSplitter_EventEndTime(String splitter_EventEndTime) {
        Splitter_EventEndTime = splitter_EventEndTime;
    }

    public String getSplitter_OfferMale() {
        return Splitter_OfferMale;
    }

    public void setSplitter_OfferMale(String splitter_OfferMale) {
        Splitter_OfferMale = splitter_OfferMale;
    }

    public String getSplitter_OfferFemale() {
        return Splitter_OfferFemale;
    }

    public void setSplitter_OfferFemale(String splitter_OfferFemale) {
        Splitter_OfferFemale = splitter_OfferFemale;
    }

    public String getSplitter_OfferAmount() {
        return Splitter_OfferAmount;
    }

    public void setSplitter_OfferAmount(String splitter_OfferAmount) {
        Splitter_OfferAmount = splitter_OfferAmount;
    }

    public String getTable_TableId() {
        return Table_TableId;
    }

    public void setTable_TableId(String table_TableId) {
        Table_TableId = table_TableId;
    }

    public String getTable_HostName() {
        return Table_HostName;
    }

    public void setTable_HostName(String table_HostName) {
        Table_HostName = table_HostName;
    }

    public String getTable_HostId() {
        return Table_HostId;
    }

    public void setTable_HostId(String table_HostId) {
        Table_HostId = table_HostId;
    }

    public String getTable_TableName() {
        return Table_TableName;
    }

    public void setTable_TableName(String table_TableName) {
        Table_TableName = table_TableName;
    }

    public String getTable_Cost() {
        return Table_Cost;
    }

    public void setTable_Cost(String table_Cost) {
        Table_Cost = table_Cost;
    }

    public String getTable_MinAmount() {
        return Table_MinAmount;
    }

    public void setTable_MinAmount(String table_MinAmount) {
        Table_MinAmount = table_MinAmount;
    }

    public String getTable_RamainingAmount() {
        return Table_RamainingAmount;
    }

    public void setTable_RamainingAmount(String table_RamainingAmount) {
        Table_RamainingAmount = table_RamainingAmount;
    }

    public String getTable_TableStatus() {
        return Table_TableStatus;
    }

    public void setTable_TableStatus(String table_TableStatus) {
        Table_TableStatus = table_TableStatus;
    }

    public String getTable_NumberOfAvailable() {
        return Table_NumberOfAvailable;
    }

    public void setTable_NumberOfAvailable(String table_NumberOfAvailable) {
        Table_NumberOfAvailable = table_NumberOfAvailable;
    }

    public String getTable_Total() {
        return Table_Total;
    }

    public void setTable_Total(String table_Total) {
        Table_Total = table_Total;
    }

    public String getTable_WhatToDo() {
        return Table_WhatToDo;
    }

    public void setTable_WhatToDo(String table_WhatToDo) {
        Table_WhatToDo = table_WhatToDo;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getMale() {
        return Male;
    }

    public void setMale(String male) {
        Male = male;
    }

    public String getFemale() {
        return Female;
    }

    public void setFemale(String female) {
        Female = female;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
