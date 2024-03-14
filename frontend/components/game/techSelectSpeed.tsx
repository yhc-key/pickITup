"use client";
import { useState } from "react";
import Image from "next/image";

import Modal from "../modal";

export default function TechSelectSpeed() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div>
      <button onClick={() => setIsModalOpen(true)}>
        <Image
          src="/images/OXQuiz.png"
          alt="OXQuiz"
          width={400}
          height={280}
          priority={true}
          className="transition-all ease-in-out hover:-translate-y-1 hover:scale-105  duration-500"
        />
      </button>
      <Modal open={isModalOpen} onClose={() => setIsModalOpen(false)}>
        모달 내용
      </Modal>
    </div>
  );
}
