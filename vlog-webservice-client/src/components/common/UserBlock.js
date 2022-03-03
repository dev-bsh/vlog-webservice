import React from "react";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import UserBlockStlyed from "../styled/commonStyled/UserBlockStyled";

const UserBlock = ({ userName, src, children, userId, email }) => {
  return (
    <UserBlockStlyed>
      <UserProfileStyled src={src} width="32px" height="32px" />
      <div style={{ marginLeft: "0.5em" }}>
        {userName}
        <br />
        {email}
      </div>
    </UserBlockStlyed>
  );
};

export default UserBlock;
