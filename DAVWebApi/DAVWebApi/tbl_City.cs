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
    
    public partial class tbl_City
    {
        public int col_CityID { get; set; }
        public string col_CityName { get; set; }
        public string col_CityCode { get; set; }
        public int col_StateID { get; set; }
    
        public virtual tbl_State tbl_State { get; set; }
    }
}
