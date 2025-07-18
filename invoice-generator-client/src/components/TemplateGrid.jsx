import React, { useState } from 'react';
import { templates } from '../assets/assets';
import './TemplateGrid.css';

const TemplateGrid = ({ onTemplateClick }) => {
  const [hoveredId, setHoveredId] = useState(null);

  return (
    <div className="row g-3">
      {templates.map(({ id, label, image, hoverColor }) => (
        <div className="col-12 col-sm-6 col-lg-4" key={id}>
          <div
            className="template-hover rounded cursor-pointer"
            title={label}
            onMouseEnter={() => setHoveredId(id)}
            onMouseLeave={() => setHoveredId(null)}
            onClick={() => onTemplateClick(id)}
            style={{
              borderColor: hoveredId === id ? hoverColor : '#dee2e6', // Bootstrap's default border color
              borderWidth: '2px',
              borderStyle: 'solid',
            }}
          >
            <img src={image} alt={label} className="w-100" loading="lazy" />
            <div className="p-2 text-center fw-medium">{label}</div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default TemplateGrid;