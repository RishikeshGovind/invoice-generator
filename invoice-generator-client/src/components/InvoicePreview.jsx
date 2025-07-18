import React, { forwardRef } from 'react';
import Template1 from '../templates/Template1/Template1';
import Template2 from '../templates/Template2/Template2'; // import your other templates
import Template3 from '../templates/Template3/Template3';
import Template4 from '../templates/Template4/Template4';
import Template5 from '../templates/Template5/Template5';
import Template6 from '../templates/Template6/Template6';
import { formatInvoiceData } from '../util/formatInvoiceData';

const InvoicePreview = forwardRef(({ invoiceData, template }, ref) => {
  const formattedData = formatInvoiceData(invoiceData);

  const renderTemplate = () => {
    switch (template) {
      case 'template1':
        return <Template1 data={formattedData} />;
      case 'template2':
        return <Template2 data={formattedData} />;
      case 'template3':
        return <Template3 data={formattedData} />;
      case 'template4':
        return <Template4 data={formattedData} />;
      case 'template5':
        return <Template5 data={formattedData} />;
      case 'template6':
        return <Template6 data={formattedData} />;
      default:
        return <Template1 data={formattedData} />;
    }
  };

  // const SelectedTemplate = templateComponents[template] || templateComponents["template1"]]

  return (
    <div ref={ref} className="invoice-preview container py-2 px-2 overflow-x-auto">
      {renderTemplate()}
      {/* <SelectedTemplate data = {formattedData} /> */}
    </div>
  );
});

export default InvoicePreview;