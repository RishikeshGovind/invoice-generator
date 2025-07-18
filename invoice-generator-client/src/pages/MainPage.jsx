import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import 'bootstrap-icons/font/bootstrap-icons.css';
import { AppContext } from "../context/AppContext.jsx";
import InvoiceForm from "../components/InvoiceForm.jsx";
import TemplateGrid from "../components/TemplateGrid.jsx";
import toast from "react-hot-toast";

const MainPage = () => {

  const [isEditingTitle, setIsEditingTitle] = useState(false);

  const navigate  = useNavigate();

  const {
    invoiceTitle, setInvoiceTitle, 
    invoiceData,
    setInvoiceData,
    setSelectedTemplate,
  
  } = useContext(AppContext);

  const handleTemplateClick = (templateId) => {
     const hasInvalidItem = invoiceData.items.some(
      (item) => !item.qty || !item.amount 
     );

     if(hasInvalidItem){
      toast.error("Please enter quantity and amount for all items.");
     }
     setSelectedTemplate(templateId);
     console.log(templateId);

     navigate('/preview');
     
  }

  const handleTitleChange = (e) => {
    const newTitle = e.target.value;
    setInvoiceTitle(newTitle);
    setInvoiceData((prev) => ({
      ...prev,
      title: newTitle,
    }));
  };

  const handleTitleEdit = () =>{
     setIsEditingTitle(true);
  }

  const handleTitleBlur = () => {
    setIsEditingTitle(false);
  }

  return (
    <div className="mainpage container-fluid bg-light min-vh-100 py-4">
      <div className="container">

        {/* Title bar */}
        <div className="bg-white border rounded shadow-sm p-3 mb-4">
          <div className="d-flex align-items-center">
            {isEditingTitle ? (
              <input type="text"
                     className="form-control me-2"
                     autoFocus 
                     onBlur={handleTitleBlur}
                     onChange={handleTitleChange}
                     value={invoiceTitle}
              />
            ) : (
              <>
                 <h5 className="mb-0 me-2">
                    {invoiceTitle}
                 </h5>
                 <button className="btn btn-sm p-0 border-0 bg-transparent"
                         onClick={handleTitleEdit}
                 >
                  <i className="bi bi-pencil text-primary" style={{ fontSize: '20px' }}></i>
                 </button>
              </>
              
            )} 
          </div>

        </div>

        {/* Invoice Form and template grid*/}
        <div className="row g-4 align-items-stretch">
          {/* Invoice Form */}
          <div className="col-12 col-lg-6 d-flex">
            <div className="bg-white border rounded shadow-sm p-4 w-100">
              <InvoiceForm />
            </div>

          </div>

          {/* Template Grid */}
          <div className="col-12 col-lg-6 d-flex">
            <div className="bg-white border rounded shadow-sm p-4 w-100">
              <TemplateGrid onTemplateClick={handleTemplateClick} />
            </div>
          </div>
        </div>

      </div>
    </div>
  )
}

export default MainPage