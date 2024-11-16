'use client';

import React, { useState } from 'react';
import Image from 'next/image';
import TreeImg from '@/assets/images/tree/tree.png';
import * as styles from './tree.css';
import { Message } from '@/types/tree/types';
import MessageItem from '@/components/tree/MessageItem';

const decorations = Array(7)
  .fill(null)
  .map((_, index) => `/images/treeObj${index + 1}.png`);

const decorationPositions = [
  { top: '18%', left: '50%' },
  { top: '36%', left: '38%' },
  { top: '36%', left: '62%' },
  { top: '52%', left: '26%' },
  { top: '50%', left: '50%' },
  { top: '52%', left: '74%' },
  { top: '69%', left: '18%' },
  { top: '66%', left: '38%' },
  { top: '66%', left: '62%' },
  { top: '69%', left: '82%' },
];

type TreeSliderProps = {
  messages: Message[];
  isFlipping: boolean;
  showDecorations: boolean;
};

const Tree = ({ messages, isFlipping, showDecorations }: TreeSliderProps) => {
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const [selectDecoration, setSelectDecoration] = useState<string>('');

  const handleDecorationClick = (message: Message, decoration: string) => {
    setSelectedMessage(message);
    setSelectDecoration(decoration);
  };

  const handleCloseModal = () => {
    setSelectedMessage(null);
  };

  return (
    <div className={styles.container}>
      <div className={`${styles.treeImage} ${isFlipping ? styles.flip : ''}`}>
        <Image src={TreeImg} alt="tree Image" className={styles.treeImage} />
      </div>
      {showDecorations &&
        messages.map((message, index) => (
          <Image
            key={index}
            src={decorations[index % decorations.length]}
            alt={`decoration ${index + 1}`}
            className={styles.decoration}
            style={decorationPositions[index]}
            onClick={() =>
              handleDecorationClick(
                message,
                decorations[index % decorations.length]
              )
            }
            width={58}
            height={58}
          />
        ))}

      {selectedMessage && (
        <MessageItem
          message={selectedMessage}
          decoration={selectDecoration}
          onClose={handleCloseModal}
        />
      )}
    </div>
  );
};

export default Tree;
