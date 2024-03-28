"use client";
import { useState } from "react";
import Image from "next/image";

interface FalseBtnProps {
  onNextClick: () => void;
}

export default function FalseBtn({ onNextClick }: FalseBtnProps) {
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
        className={`flex items-center justify-center cursor-pointer w-80 h-40 rounded-3xl drop-shadow-md transition-all ease-in duration-300 ${hovered ? "bg-f5red-100 scale-105" : "bg-f5gray-200"}`}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
        onClick={onNextClick}
      >
        <Image
          src={hovered ? "/images/falseRed.png" : "/images/falseGray.png"}
          alt={hovered ? "falseRed" : "falseGray"}
          width={120}
          height={120}
          className="transition-all duration-300 ease-in"
        />
      </div>
    </div>
  );
}
