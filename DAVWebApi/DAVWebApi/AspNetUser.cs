//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace SSKWebApi
{
    using System;
    using System.Collections.Generic;
    
    public partial class AspNetUser
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public AspNetUser()
        {
            this.AspNetUserClaims = new HashSet<AspNetUserClaim>();
            this.AspNetUserLogins = new HashSet<AspNetUserLogin>();
            this.tbl_BackGroundCheck = new HashSet<tbl_BackGroundCheck>();
            this.tbl_BusinessDirectory = new HashSet<tbl_BusinessDirectory>();
            this.tbl_DeviceIds = new HashSet<tbl_DeviceIds>();
            this.tbl_Jobs = new HashSet<tbl_Jobs>();
            this.tbl_PartyStatus = new HashSet<tbl_PartyStatus>();
            this.tbl_Photo = new HashSet<tbl_Photo>();
            this.tbl_Rating = new HashSet<tbl_Rating>();
            this.tbl_Requirements = new HashSet<tbl_Requirements>();
            this.tbl_UserAlbum = new HashSet<tbl_UserAlbum>();
            this.tbl_UserMessages = new HashSet<tbl_UserMessages>();
            this.AspNetRoles = new HashSet<AspNetRole>();
        }
    
        public string Id { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public bool EmailConfirmed { get; set; }
        public string PasswordHash { get; set; }
        public string SecurityStamp { get; set; }
        public string PhoneNumber { get; set; }
        public bool PhoneNumberConfirmed { get; set; }
        public bool TwoFactorEnabled { get; set; }
        public Nullable<System.DateTime> LockoutEndDateUtc { get; set; }
        public bool LockoutEnabled { get; set; }
        public int AccessFailedCount { get; set; }
        public string UserName { get; set; }
        public string Address { get; set; }
        public System.DateTime DateOfBirth { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string PostalCode { get; set; }
        public int RollNo { get; set; }
        public System.DateTime JoiningYear { get; set; }
        public System.DateTime LeavingYear { get; set; }
        public string Designation { get; set; }
        public string Posting { get; set; }
        public string BloodGroup { get; set; }
        public Nullable<decimal> Latitude { get; set; }
        public Nullable<decimal> Longitude { get; set; }
        public string House { get; set; }
        public string Department { get; set; }
        public Nullable<System.DateTime> LocationTime { get; set; }
        public string School { get; set; }
        public bool PhoneVisible { get; set; }
        public bool ShowLocation { get; set; }
        public string Profession { get; set; }
        public string ProfileLink { get; set; }
    
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<AspNetUserClaim> AspNetUserClaims { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<AspNetUserLogin> AspNetUserLogins { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_BackGroundCheck> tbl_BackGroundCheck { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_BusinessDirectory> tbl_BusinessDirectory { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_DeviceIds> tbl_DeviceIds { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_Jobs> tbl_Jobs { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_PartyStatus> tbl_PartyStatus { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_Photo> tbl_Photo { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_Rating> tbl_Rating { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_Requirements> tbl_Requirements { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_UserAlbum> tbl_UserAlbum { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_UserMessages> tbl_UserMessages { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<AspNetRole> AspNetRoles { get; set; }
    }
}
