import React from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";
import { clickedGoHome } from "../../store/lobby/lobby-store";
import * as S from "./styles/Header.styles";

const Brand = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const handleClick = () => {
    history.push("/");
    dispatch(clickedGoHome());
  };

  return <S.Brand onClick={handleClick}>Revelation 21:8</S.Brand>;
};

export default Brand;
