"use client";
import { useState } from "react";
import Image from "next/image";

import trueBlue from "/public/images/trueBlue.png";
import trueGray from "/public/images/trueGray.png";

interface trueBtnProps {
  onNextClick: () => void;
}

export default function FalseBtn({ onNextClick }: trueBtnProps) {
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
    <div className="mx-10 my-5">
      <div
        className={`flex items-center justify-center cursor-pointer w-80 h-40  rounded-3xl drop-shadow-md transition-all ease-in-out duration-300 ${hovered ? "bg-f5blue-100 scale-105" : "bg-f5gray-200"}`}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
        onClick={onNextClick}
      >
        <Image
          src={hovered ? trueBlue : trueGray}
          alt={hovered ? "trueBlue" : "trueGray"}
          className="transition-all duration-300 ease-in-out"
        />
      </div>
    </div>
  );
}
