import React from "react";
import UploadStyled from "../styled/commonStyled/UploadStyled";
import UploadButtonStyled from "../styled/commonStyled/UploadButtonStyled";

import { Link } from "react-router-dom";
import { MdBackup } from "react-icons/md";
const UploadButton = () => {
  return (
    <UploadStyled>
      <UploadButtonStyled>
        <Link to="/upload">
          <MdBackup />
        </Link>
      </UploadButtonStyled>
    </UploadStyled>
  );
};

export default UploadButton;
