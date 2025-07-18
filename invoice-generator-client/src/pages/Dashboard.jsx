import React, { useEffect, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import {AppContext, initialInvoiceData} from '../context/AppContext.jsx';
import { toast } from 'react-hot-toast';
import { getAllInvoices } from '../service/InvoiceService';
import { Plus } from 'lucide-react';
import { formatDate } from '../util/formatInvoiceData.js';
import { useAuth } from '@clerk/clerk-react';

const Dashboard = () => {

  const [invoices, setInvoices] = useState([]);
  const {baseUrl, setInvoiceData, setSelectedTemplate, setInvoiceTitle} = useContext(AppContext);
  const navigate =  useNavigate();
  const { getToken } = useAuth();

  useEffect(()=>{
    const fetchInvoices = async () => {

      try{
        const token = await getToken();
        const response = await getAllInvoices(baseUrl, token);
        setInvoices(response.data);
      } catch(error){
        toast.error("Failed to load the invoices", error);
      }
    }
    fetchInvoices();
  }, [baseUrl]);

  const handleViewClick = (invoice) => {
    setInvoiceData(invoice);
    setSelectedTemplate(invoice.template || "template1");
    setInvoiceTitle(invoice.title || "New Invoice");
    navigate('/preview');
  }

  const handleCreateNew = () => {
    //reset to initial stage from context
    setInvoiceTitle("New Invoice");
    setSelectedTemplate("template1");
    setInvoiceData(initialInvoiceData);
    navigate('/generate');
  }

  return (
    <div className="container py-5">
      <div className="row row-cols-1 row-cols-sm-2 row-cols-md-1 row-cols-lg-5 g-4">
        {/* Create New Invoice card */}
        <div className="col">
          <div className="card h-100 d-flex justify-content-center align-items-center border border-2 border-light shadow-sm cursor-pointer" 
               style={{minHeight: '270px'}}
               onClick={handleCreateNew}>
             <Plus size={48}/>
             <p className="mt-3 fw-medium">Create New Invoice</p>
         
          </div>
        </div>

        {/* Render the existing invoices */}
        {invoices.map((invoice, idx) => (
          <div className="col" key={idx}>
            <div className="card h-100 shadow-sm cursor-pointer" 
                 style={{minHeight: '270px'}}
                 onClick={() => handleViewClick(invoice)}>

               {invoice.thumbnailUrl && (
                 <img src={invoice.thumbnailUrl} 
                      alt="Invoice thumbnail" 
                      className="card-img-top" 
                      style={{height: '200px', objectFit: 'cover'}}
                  />
               )}

               <div className="card-body">
                   <h6 className="card-title mb-1"> </h6>
                   <small className="text-muted">
                    Last Updated: {formatDate(invoice.createdAt )}
                   </small>
               </div>
            </div>
          </div>
        ))}
      </div>
    </div> 
  )
}

export default Dashboard