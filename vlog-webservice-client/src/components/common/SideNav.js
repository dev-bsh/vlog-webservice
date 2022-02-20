import React from "react";
import SideNavStyled from "../styled/commonStyled/SideNavStyled";
import RecommnadationUser from "../common/RecommandationUser";
import RecommandationTag from "../common/RecommandationTag";
import Footer from "../common/Footer";

const SideNav = () => {
  return (
    <SideNavStyled>
      <RecommnadationUser />
      <RecommandationTag />
      <Footer />
    </SideNavStyled>
  );
};

export default SideNav;
