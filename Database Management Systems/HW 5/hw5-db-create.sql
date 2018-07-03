drop table Doctor cascade constraints;
drop table Patient cascade constraints;
drop table Pharmaceutical_Co cascade constraints;
drop table Pharmacy cascade constraints;
drop table Drug cascade constraints;
drop table Prescription cascade constraints;
drop table Sell cascade constraints;
drop table Contract cascade constraints;

create table DOCTOR (
Doctor_Lic_No number(10),
Doctor_Lic_State varchar2(10),
FirstName varchar2(15),
MiddleName varchar2(15),
LastName varchar2(20),
Speciality varchar2(30),
YearofExperience number(2),
constraint pk_doctor primary key( Doctor_Lic_No, Doctor_Lic_State)
);

create table PATIENT (
SSN number(9),
FirstName varchar2(15),
MiddleName varchar2(15),
LastName varchar2(20),
Address varchar2(64),
DateOfBirth date,
PCP_Lic_No number(10),
PCP_Lic_State varchar2(10),
constraint pk_patient primary key(SSN),
constraint fk_patient_pcp foreign key (PCP_Lic_No, PCP_Lic_State)
           references DOCTOR (Doctor_Lic_No, Doctor_Lic_State)
);

create table PHARMACEUTICAL_CO (
PCM_Registration varchar2(20),
Name varchar2(20),
Phone number(10),
constraint pk_pco primary key(PCM_Registration));

create table PHARMACY (
Name varchar2(20),
Phone number(10),
Address varchar2(64),
constraint pk_parmacy primary key(Name),
constraint un_pharmacy_address unique (Address),
constraint un_pharmacy_phone unique (Phone) );

create table DRUG (
Name varchar2(20),
Formula varchar2(30),
Manufacturer_Registration varchar2(20),
constraint pk_drug primary key(Name),
constraint fk_drug_mreg foreign key(Manufacturer_Registration) 
      references PHARMACEUTICAL_CO (PCM_Registration)
);

create table PRESCRIPTION (
Doctor_Lic_No number(10),
Doctor_Lic_State varchar2(10),
Patient number(9),
Drug varchar2(20),
Quantity number(2),
prescDate date,
constraint pk_prescription primary key( Doctor_Lic_No, Doctor_Lic_State, Patient, Drug, Quantity),
constraint fk_presc_doc foreign key( Doctor_Lic_No, Doctor_Lic_State)
       references DOCTOR( Doctor_Lic_No, Doctor_Lic_State),
constraint fk_presc_patient foreign key( Patient)
       references PATIENT( SSN),
constraint fk_presc_drug foreign key( Drug)
       references DRUG( Name) );

create table SELL (
Store varchar2(20),
Price number(4,2),
Drug varchar2(20),
constraint pk_sell primary key( Store, Drug),
constraint fk_sell_store foreign key( Store)
     references PHARMACY( Name),
constraint fk_sell_drug foreign key( Drug)
     references DRUG( Name) );

create table CONTRACT (
Store varchar2(20),
Manufacturer_Registration varchar2(20),
StartDate date,
EndDate date,
constraint pk_contract primary key( Store, Manufacturer_Registration),
constraint fk_constract_store foreign key( Store)
      references PHARMACY( Name),
constraint fk_contract_mreg foreign key( Manufacturer_Registration)
      references PHARMACEUTICAL_CO( PCM_Registration) );

