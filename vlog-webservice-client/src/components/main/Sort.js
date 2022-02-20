import React, { useState, useEffect } from "react";
import SortStyled from "../styled/mainStyled/SortStyled";
import ButtonStyled from "../styled/commonStyled/ButtonStyled";
import { MdTrendingUp } from "react-icons/md";
import { MdAvTimer } from "react-icons/md";
import { Route, Routes, NavLink } from "react-router-dom";
import Main from "./Main";
import Recent from "../recent/Recent";
import { useDispatch, useSelector } from "react-redux";
import { fetchPost, fetchPostPopular } from "../../redux/module/post";

const navLinkStyled = ({ isActive }) => {
  return {
    borderBottom: isActive ? "2px solid black" : "white",
  };
};

const Sort = () => {
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();

  const handleClick = async (e) => {
    let target = e.target.dataset.link;
    if (target === "popular") {
      dispatch(fetchPostPopular());
    } else {
      dispatch(fetchPost());
    }
  };

  return (
    <SortStyled>
      <NavLink style={navLinkStyled} to="/">
        <ButtonStyled data-link="popular">
          <MdTrendingUp />
          인기
        </ButtonStyled>
      </NavLink>
      <NavLink style={navLinkStyled} to="/recent">
        <ButtonStyled data-link="recent">
          <MdAvTimer />
          최신
        </ButtonStyled>
      </NavLink>
    </SortStyled>
  );
};

export default Sort;
