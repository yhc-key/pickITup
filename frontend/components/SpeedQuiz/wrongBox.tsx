"use-client";
import { useState } from "react";
import Image from "next/image";

import wrong from "/public/images/wrong.png";

interface wrongBoxProps {
  user: string;
  answer: string;
}

export default function WrongBox({ user, answer }: wrongBoxProps) {
  const [hovered, setHovered] = useState(false);

  // 마우스 올리면 hover 유무 true
  const handleMouseEnter = (): void => {
    setHovered(true);
  };

  // 마우스 떠나면 hover 유무 false
  const handleMouseLeave = (): void => {
    setHovered(false);
  };
  
  return (
    <div
      className={`relative p-5 text-center align-bottom cursor-pointer drop-shadow-md w-52 h-28 rounded-3xl bg-f5red-100 ${hovered ? "bg-f5gray-400 bg-opacity-50 transition-all ease-in-out" : ""}`}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <Image src={wrong} alt="wrong" />
      <div className="text-lg font-semibold text-center">
        {hovered ? answer : user}
      </div>
    </div>
  );
}
