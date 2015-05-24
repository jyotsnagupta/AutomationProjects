
drop table #temp
create table #temp(card numeric(20) , ownerid numeric(10))
insert into #temp values(91010100000024 ,400)

update contact set Address1=NULL,Address2=NULL,Address3=NULL,District=NULL,City=NULL,State=NULL,Country=NULL,Zip_Code=NULL,
Email=NULL,Email2=NULL,PostBox=NULL,Home_Phone=NULL,Office_Phone=NULL,Office_Phone2=NULL,Mobile_Number=NULL,Fax_Number=NULL
WHERE contactid in (select contactid from customer where customerid in
              ( select customerid from card where card_number  in  (select card from #temp) ))
go           
update Customer set Title=NULL,First_Name=NULL,Middle_Name=NULL,Last_Name=NULL,Gender=NULL,Birth_Date=NULL,Race=NULL,
Nationality=NULL,Designation=NULL,Department=NULL,Branch_Name=NULL,Customer_Class=NULL,Category=NULL,Type=NULL,Segment=NULL,
Membership_Fee=NULL,Membership_Fee_Status=NULL,User_IdentificationID=NULL,Marital_Status=NULL,Utility_Status='GH' ,
Status_Reason=NULL,VIP_Status=NULL,TnC_Status=NULL,Customer_HouseholdID=NULL,ParentID=NULL,Relationship=NULL,
Father_Name=NULL,Father_Birth_Date=NULL,Mother_Name=NULL,Mother_Birth_Date=NULL,Spouse_Name=NULL,Spouse_Birth_Date=NULL,
Child1_Name=NULL,Child1_Birth_Date=NULL,Child2_Name=NULL,Child2_Birth_Date=NULL,Child3_Name=NULL,Child3_Birth_Date=NULL,
Qualification=NULL,Occupation=NULL,Marriage_Anniversary=NULL,Preferred_Contact=NULL,
Enrol_StoreID=NULL,Enrol_TillID=NULL,Enrol_UserID=NULL where customerid in (select customerid from card where card_number in  (select card from #temp))
go
update card set Earned_Points=0,Redeemed_Points=0,Expired_Points=0,Booked_Points=0,Redeem_Booked_Points=0,Redeemed_Used_For_Expiry=0
,Is_Register=NULL , Card_Type=NULL,Mobile_Number=NULL,Status=22,Number_Of_Loads=NuLL,Number_Of_Redemptions=NULL,-- CustomerID=null,
referral_customer=Null,primary_customerid=NUll where card_number  in  (select card from #temp) --and Program_TypeID=3
go
delete from User_Role where User_InfoID in ( select User_InfoID from User_Info where OwnerID  in  (select ownerid from #temp))
delete USER_INFO WHERE contactid in (select contactid from customer where   customerid in
              ( select customerid from card where card_number  in  (select card from #temp) ))
  go  
delete from CUSTOMER_POINTS_SUMMARY where customerid in (select customerid from card where card_number  in  (select card from #temp))
go
delete from CUSTOMER_HOUSEHOLD where Customer_HouseholdID in (select Customer_HouseholdID  from customer where customerid in
              ( select customerid from card where card_number  in  (select card from #temp) ))
              go
delete from Customer_Survey where customerid in ( select customerid from card where card_number  in  (select card from #temp))
go
delete from loyalty_Customer_Offer where customerid in ( select customerid from card where card_number  in  (select card from #temp))
go
delete from USER_IDENTIFICATION where User_IdentificationID in (select User_IdentificationID
              from customer where customerid in ( select customerid from card where card_number  in  (select card from #temp)))             
              go
delete from Transaction_Billing_Data where Loyalty_TransactionID in (select Loyalty_TransactionID from 
loyalty_transaction where cardpan  in  (select card from #temp))
go
delete from loyalty_transaction where cardpan  in  (select card from #temp)
go
print 'delete loyalty_transaction '

delete from User_Role_Permission where User_Role_TypeID in ( select User_Role_TypeID from User_Role_Type where User_Role_TypeID not in ( 643,644) and OwnerID  in  (select ownerid from #temp) )
GO
print 'delete User_Role_Permission '

delete from User_Role_Type where User_Role_TypeID not in ( 643,644) and OwnerID  in  (select ownerid from #temp) 
print 'delete User_Role_Type'
GO

--delete User_Info where Username like 'bbuser1'
delete from card where Card_SchemeId in( select Card_SchemeID from Card_Scheme where OwnerID  in  (select ownerid from #temp))
print 'delete card'
GO

delete Stock_Order where MerchantId  in  (select ownerid from #temp)
print 'delete Stock_Order'
GO

delete from Stock_Order_Details where Card_SchemeID in (select Card_SchemeID from Card_Scheme where OwnerID  in  (select ownerid from #temp))
print 'delete Stock_Order_Details'
GO

delete Card_Scheme_Group where MerchantId  in  (select ownerid from #temp)
print 'delete Card_Scheme_Group'
--select * from Card_Scheme_Group where MerchantId  in  (select ownerid from #temp)
GO

delete from BIN_RANGE where OwnerID  in  (select ownerid from #temp)
print 'delete BIN_RANGE'
GO

delete from Bin_Range_Product where MerchantId  in  (select ownerid from #temp)
print 'delete Bin_Range_Product'
GO

delete from Bin_Range_Corporate where MerchantId  in  (select ownerid from #temp)
print 'delete Bin_Range_Corporate'
GO

delete from Denominations where OwnerID  in  (select ownerid from #temp)
print 'delete Denominations'
GO

delete from customer where OwnerID  in  (select ownerid from #temp)
print 'delete customer'
GO

delete from Card_Scheme where OwnerID  in  (select ownerid from #temp)
print 'delete Card_Scheme'
GO

delete from Card_Membership_Tier where Card_MembershipID in (select Card_MembershipID from Card_Membership where OwnerID  in  (select ownerid from #temp))
print 'delete Card_Membership_Tier'
GO

delete from Card_Membership where OwnerID  in  (select ownerid from #temp)
print 'delete Card_Membership'
GO

delete from User_Role where User_InfoID in ( select User_InfoID from User_Info where OwnerID  in  (select ownerid from #temp))
print 'delete User_Role'
GO

delete from User_Type where User_InfoID in ( select User_InfoID from User_Info where OwnerID  in  (select ownerid from #temp))
print 'delete User_Type'
GO

delete from User_Info where OwnerID  in  (select ownerid from #temp)
print 'delete User_Info'
GO
--select * from card where Card_SchemeId in( select Card_SchemeID from Card_Scheme where OwnerID  in  (select ownerid from #temp))

GO